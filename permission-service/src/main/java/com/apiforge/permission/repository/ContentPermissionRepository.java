package com.apiforge.permission.repository;

import com.apiforge.permission.model.ContentPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContentPermissionRepository extends JpaRepository<ContentPermission, Long> {
    List<ContentPermission> findByContentTypeApiId(String contentTypeApiId);
    Optional<ContentPermission> findByContentTypeApiIdAndAction(String contentTypeApiId, String action);
}