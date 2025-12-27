package com.apiforge.permission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiPermissionDto {
    private Long id;
    private String contentTypeApiId;
    private String endpoint;
    private String method;
    private Set<String> allowedRoles;
    private LocalDateTime createdAt;
}