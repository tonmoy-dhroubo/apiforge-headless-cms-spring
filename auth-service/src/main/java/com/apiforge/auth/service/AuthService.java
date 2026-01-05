package com.apiforge.auth.service;

import com.apiforge.auth.dto.AuthResponse;
import com.apiforge.auth.dto.LoginRequest;
import com.apiforge.auth.dto.RegisterRequest;
import com.apiforge.auth.model.Role;
import com.apiforge.auth.model.User;
import com.apiforge.auth.repository.RoleRepository;
import com.apiforge.auth.repository.UserRepository;
import com.apiforge.common.exception.CustomExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomExceptions.ConflictException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomExceptions.ConflictException("Email already exists");
        }

        // Assign REGISTERED role by default
        Role registeredRole = roleRepository.findByName("REGISTERED")
                .orElseGet(() -> {
                    Role newRole = Role.builder()
                            .name("REGISTERED")
                            .description("Default registered user")
                            .build();
                    return roleRepository.save(newRole);
                });

        Set<Role> roles = new HashSet<>();
        roles.add(registeredRole);

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .roles(roles)
                .enabled(true)
                .build();

        user = userRepository.save(user);

        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        String token = jwtService.generateToken(user.getUsername(), user.getId(), roleNames);
        String refreshToken = jwtService.generateRefreshToken(user.getUsername(), user.getId(), roleNames);

        return AuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(roleNames)
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        String identifier = request.getUsername();
        if ((identifier == null || identifier.isBlank()) && request.getEmail() != null) {
            identifier = request.getEmail();
        }

        if (identifier == null || identifier.isBlank()) {
            throw new CustomExceptions.UnauthorizedException("Username or email is required");
        }

        User user = userRepository.findByUsername(identifier).orElse(null);
        if (user == null) {
            user = userRepository.findByEmail(identifier).orElse(null);
        }

        if (user == null) {
            throw new CustomExceptions.UnauthorizedException("Invalid credentials");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomExceptions.UnauthorizedException("Invalid credentials");
        }

        if (!user.getEnabled()) {
            throw new CustomExceptions.ForbiddenException("Account is disabled");
        }

        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        String token = jwtService.generateToken(user.getUsername(), user.getId(), roleNames);
        String refreshToken = jwtService.generateRefreshToken(user.getUsername(), user.getId(), roleNames);

        return AuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(roleNames)
                .build();
    }

    public boolean validateToken(String token) {
        return jwtService.validateToken(token);
    }

    public AuthResponse refresh(String refreshToken) {
        if (!jwtService.validateRefreshToken(refreshToken)) {
            throw new CustomExceptions.UnauthorizedException("Invalid refresh token");
        }

        Long userId = jwtService.extractRefreshUserId(refreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomExceptions.UnauthorizedException("Invalid refresh token"));

        if (!user.getEnabled()) {
            throw new CustomExceptions.ForbiddenException("Account is disabled");
        }

        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        String token = jwtService.generateToken(user.getUsername(), user.getId(), roleNames);
        String nextRefreshToken = jwtService.generateRefreshToken(user.getUsername(), user.getId(), roleNames);

        return AuthResponse.builder()
                .token(token)
                .refreshToken(nextRefreshToken)
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(roleNames)
                .build();
    }
}
