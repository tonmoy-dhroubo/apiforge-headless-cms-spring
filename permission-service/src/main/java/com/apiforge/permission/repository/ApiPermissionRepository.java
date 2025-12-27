package com.apiforge.permission.repository;

import com.apiforge.permission.model.ApiPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApiPermissionRepository extends JpaRepository<ApiPermission, Long> {
    List<ApiPermission> findByContentTypeApiId(String contentTypeApiId);
    Optional<ApiPermission> findByContentTypeApiIdAndEndpointAndMethod(
            String contentTypeApiId, String endpoint, String method);
}