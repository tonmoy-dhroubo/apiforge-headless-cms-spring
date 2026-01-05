package com.apiforge.auth.service;

import com.apiforge.common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JwtService {

    @Autowired
    private JwtUtil jwtUtil;

    public String generateToken(String username, Long userId, List<String> roles) {
        return jwtUtil.generateToken(username, userId, roles);
    }

    public String generateRefreshToken(String username, Long userId, List<String> roles) {
        return jwtUtil.generateRefreshToken(username, userId, roles);
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    public boolean validateRefreshToken(String token) {
        return jwtUtil.validateRefreshToken(token);
    }

    public String extractUsername(String token) {
        return jwtUtil.extractUsername(token);
    }

    public Long extractUserId(String token) {
        return jwtUtil.extractUserId(token);
    }

    public List<String> extractRoles(String token) {
        return jwtUtil.extractRoles(token);
    }

    public String extractRefreshUsername(String token) {
        return jwtUtil.extractRefreshUsername(token);
    }

    public Long extractRefreshUserId(String token) {
        return jwtUtil.extractRefreshUserId(token);
    }

    public List<String> extractRefreshRoles(String token) {
        return jwtUtil.extractRefreshRoles(token);
    }
}
