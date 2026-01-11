package com.apiforge.api_gateway.filter;

import com.apiforge.common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final JwtUtil jwtUtil;
    private final WebClient.Builder webClientBuilder;
    private final String permissionServiceUrl;

    @Autowired
    public AuthenticationFilter(
            JwtUtil jwtUtil,
            WebClient.Builder webClientBuilder,
            @Value("${apiforge.permission-service-url:http://localhost:8085}") String permissionServiceUrl) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
        this.webClientBuilder = webClientBuilder;
        this.permissionServiceUrl = permissionServiceUrl;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // Skip authentication for login and register endpoints
            if (isAuthEndpoint(request)) {
                return chain.filter(exchange);
            }

            // Check for Authorization header
            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || authHeader.isBlank()) {
                return authorizePublicAccess(exchange, chain);
            }
            if (!authHeader.startsWith("Bearer ")) {
                return onError(exchange, "Invalid authorization header", HttpStatus.UNAUTHORIZED);
            }

            String token = authHeader.substring(7);

            try {
                if (!jwtUtil.validateToken(token)) {
                    return onError(exchange, "Invalid or expired token", HttpStatus.UNAUTHORIZED);
                }

                // Extract user info and add to request headers so downstream services can use it
                String username = jwtUtil.extractUsername(token);
                Long userId = jwtUtil.extractUserId(token);
                List<String> roles = jwtUtil.extractRoles(token);

                ServerHttpRequest modifiedRequest = request.mutate()
                        .header("X-User-Id", userId != null ? userId.toString() : "")
                        .header("X-Username", username)
                        .header("X-User-Roles", String.join(",", roles))
                        .build();

                return chain.filter(exchange.mutate().request(modifiedRequest).build());

            } catch (Exception e) {
                return onError(exchange, "Token validation failed", HttpStatus.UNAUTHORIZED);
            }
        };
    }

    private boolean isAuthEndpoint(ServerHttpRequest request) {
        String path = request.getPath().toString();
        // Allow public access to auth endpoints and media files
        return path.contains("/api/auth/login")
                || path.contains("/api/auth/register")
                || path.contains("/api/auth/validate")
                || path.contains("/api/media/files"); // Allow public viewing of images
    }

    private Mono<Void> authorizePublicAccess(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().toString();
        String contentTypeApiId = resolveContentTypeApiId(path);
        if (contentTypeApiId == null) {
            return onError(exchange, "Authorization required", HttpStatus.UNAUTHORIZED);
        }

        Map<String, Object> payload = Map.of(
                "contentTypeApiId", contentTypeApiId,
                "endpoint", normalizeEndpoint(path),
                "method", request.getMethod() != null ? request.getMethod().name() : "",
                "userRoles", List.of("PUBLIC")
        );

        return webClientBuilder.baseUrl(permissionServiceUrl)
                .build()
                .post()
                .uri("/api/permissions/api/check")
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> Boolean.TRUE.equals(response.get("data")))
                .flatMap(allowed -> {
                    if (!allowed) {
                        return onError(exchange, "Forbidden", HttpStatus.FORBIDDEN);
                    }
                    ServerHttpRequest modifiedRequest = request.mutate()
                            .header("X-User-Id", "")
                            .header("X-Username", "public")
                            .header("X-User-Roles", "PUBLIC")
                            .build();
                    return chain.filter(exchange.mutate().request(modifiedRequest).build());
                })
                .onErrorResume(ex -> onError(exchange, "Permission check failed", HttpStatus.FORBIDDEN));
    }

    private String resolveContentTypeApiId(String path) {
        if (!path.startsWith("/api/")) {
            return null;
        }
        if (path.startsWith("/api/content/")) {
            String trimmed = path.substring("/api/content/".length());
            String[] parts = trimmed.split("/");
            return parts.length > 0 && !parts[0].isBlank() ? parts[0] : null;
        }
        String trimmed = path.substring("/api/".length());
        String[] parts = trimmed.split("/");
        return parts.length > 0 && !parts[0].isBlank() ? parts[0] : null;
    }

    private String normalizeEndpoint(String path) {
        if (path.matches(".*/\\d+$")) {
            return path.replaceAll("/\\d+$", "/{id}");
        }
        return path;
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus status) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        return response.setComplete();
    }

    public static class Config {
        // Configuration properties if needed
    }
}
