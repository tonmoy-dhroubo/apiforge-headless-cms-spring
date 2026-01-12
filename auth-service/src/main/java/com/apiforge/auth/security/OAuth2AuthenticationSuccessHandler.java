package com.apiforge.auth.security;

import com.apiforge.auth.dto.AuthResponse;
import com.apiforge.auth.model.Role;
import com.apiforge.auth.model.User;
import com.apiforge.auth.repository.RoleRepository;
import com.apiforge.auth.repository.UserRepository;
import com.apiforge.auth.service.JwtService;
import com.apiforge.common.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    public OAuth2AuthenticationSuccessHandler(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String email = stringAttr(oauthUser, "email");
        if (email == null || email.isBlank()) {
            sendError(response, "OAuth2 login failed: email not provided");
            return;
        }

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            user = createUserFromOAuth(email, oauthUser);
        }

        if (!Boolean.TRUE.equals(user.getEnabled())) {
            sendError(response, "Account is disabled");
            return;
        }

        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        String token = jwtService.generateToken(user.getUsername(), user.getId(), roleNames);
        String refreshToken = jwtService.generateRefreshToken(user.getUsername(), user.getId(), roleNames);

        AuthResponse authResponse = AuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(roleNames)
                .build();

        ApiResponse<AuthResponse> body = ApiResponse.success("OAuth2 login successful", authResponse);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), body);
    }

    private User createUserFromOAuth(String email, OAuth2User oauthUser) {
        Role registeredRole = roleRepository.findByName("REGISTERED")
                .orElseGet(() -> roleRepository.save(Role.builder()
                        .name("REGISTERED")
                        .description("Default registered user")
                        .build()));

        Set<Role> roles = new HashSet<>();
        roles.add(registeredRole);

        String baseUsername = deriveUsername(email);
        String username = uniqueUsername(baseUsername);

        String firstname = stringAttr(oauthUser, "given_name");
        String lastname = stringAttr(oauthUser, "family_name");

        User user = User.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                .firstname(firstname)
                .lastname(lastname)
                .roles(roles)
                .enabled(true)
                .build();

        return userRepository.save(user);
    }

    private String deriveUsername(String email) {
        int at = email.indexOf('@');
        return at > 0 ? email.substring(0, at) : email;
    }

    private String uniqueUsername(String baseUsername) {
        String candidate = baseUsername;
        int suffix = 1;
        while (userRepository.existsByUsername(candidate)) {
            candidate = baseUsername + "-" + suffix;
            suffix++;
        }
        return candidate;
    }

    private String stringAttr(OAuth2User user, String key) {
        Object value = user.getAttributes().get(key);
        return value == null ? null : value.toString();
    }

    private void sendError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), ApiResponse.error(message));
    }
}
