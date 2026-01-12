package com.apiforge.auth.controller;

import com.apiforge.auth.dto.*;
import com.apiforge.auth.service.AuthService;
import com.apiforge.auth.service.UserService;
import com.apiforge.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(ApiResponse.success("User registered successfully", response));
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login with username or email",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "SeededAdminLogin",
                                    value = """
                                            {
                                              "username": "admin",
                                              "password": "password123"
                                            }
                                            """
                            )
                    )
            )
    )
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }

    @PostMapping("/validate")
    public ResponseEntity<ApiResponse<Boolean>> validateToken(@RequestBody Map<String, String> request) {
        boolean valid = authService.validateToken(request.get("token"));
        return ResponseEntity.ok(ApiResponse.success(valid));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refresh(@RequestBody RefreshRequest request) {
        AuthResponse response = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.success("Token refreshed", response));
    }

    @GetMapping("/oauth2/google")
    @Operation(
            summary = "Start Google OAuth2 login",
            description = "Redirects to the Google OAuth2 authorization flow.",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "302",
                    description = "Redirect to Google OAuth2"
            )
    )
    public void oauth2Google(HttpServletResponse response) throws IOException {
        response.sendRedirect("/api/auth/oauth2/authorize/google");
    }

    @GetMapping("/users")
    @Operation(
            summary = "List users",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Seeded users",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "SeededUsers",
                                    value = """
                                            {
                                              "success": true,
                                              "message": null,
                                              "data": [
                                                {
                                                  "id": 1,
                                                  "username": "super_admin",
                                                  "email": "super_admin@apiforge.com",
                                                  "firstname": "Super",
                                                  "lastname": "Admin",
                                                  "roles": ["SUPER_ADMIN", "ADMIN"],
                                                  "enabled": true
                                                },
                                                {
                                                  "id": 4,
                                                  "username": "jane",
                                                  "email": "jane.doe@apiforge.com",
                                                  "firstname": "Jane",
                                                  "lastname": "Doe",
                                                  "roles": ["REGISTERED"],
                                                  "enabled": true
                                                }
                                              ],
                                              "error": null
                                            }
                                            """
                            )
                    )
            )
    )
    public ResponseEntity<ApiResponse<List<UserDto>>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable Long id) {
        UserDto user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PutMapping("/users/{id}/roles")
    public ResponseEntity<ApiResponse<UserDto>> assignRoles(
            @PathVariable Long id,
            @RequestBody Map<String, List<String>> request) {
        UserDto user = userService.assignRolesToUser(id, request.get("roles"));
        return ResponseEntity.ok(ApiResponse.success("Roles assigned successfully", user));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
    }
}
