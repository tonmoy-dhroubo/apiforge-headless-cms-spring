package com.apiforge.permission.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "api_permissions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contentTypeApiId;

    @Column(nullable = false)
    private String endpoint; // e.g., /api/content/{contentTypeApiId}

    @Column(nullable = false)
    private String method; // GET, POST, PUT, DELETE

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "api_permission_roles", joinColumns = @JoinColumn(name = "permission_id"))
    @Column(name = "role_name")
    @Builder.Default
    private Set<String> allowedRoles = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;
}