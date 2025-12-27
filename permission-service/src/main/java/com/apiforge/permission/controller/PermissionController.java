package com.apiforge.permission.controller;

import com.apiforge.common.dto.ApiResponse;
import com.apiforge.permission.dto.ApiPermissionDto;
import com.apiforge.permission.dto.ContentPermissionDto;
import com.apiforge.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    // API Permission endpoints
    @PostMapping("/api")
    public ResponseEntity<ApiResponse<ApiPermissionDto>> createApiPermission(@RequestBody ApiPermissionDto dto) {
        ApiPermissionDto created = permissionService.createApiPermission(dto);
        return ResponseEntity.ok(ApiResponse.success("API permission created successfully", created));
    }

    @GetMapping("/api")
    public ResponseEntity<ApiResponse<List<ApiPermissionDto>>> getAllApiPermissions() {
        List<ApiPermissionDto> permissions = permissionService.getAllApiPermissions();
        return ResponseEntity.ok(ApiResponse.success(permissions));
    }

    @GetMapping("/api/content-type/{contentTypeApiId}")
    public ResponseEntity<ApiResponse<List<ApiPermissionDto>>> getApiPermissionsByContentType(
            @PathVariable String contentTypeApiId) {
        List<ApiPermissionDto> permissions = permissionService.getApiPermissionsByContentType(contentTypeApiId);
        return ResponseEntity.ok(ApiResponse.success(permissions));
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<ApiResponse<ApiPermissionDto>> getApiPermissionById(@PathVariable Long id) {
        ApiPermissionDto permission = permissionService.getApiPermissionById(id);
        return ResponseEntity.ok(ApiResponse.success(permission));
    }

    @PutMapping("/api/{id}")
    public ResponseEntity<ApiResponse<ApiPermissionDto>> updateApiPermission(
            @PathVariable Long id,
            @RequestBody ApiPermissionDto dto) {
        ApiPermissionDto updated = permissionService.updateApiPermission(id, dto);
        return ResponseEntity.ok(ApiResponse.success("API permission updated successfully", updated));
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteApiPermission(@PathVariable Long id) {
        permissionService.deleteApiPermission(id);
        return ResponseEntity.ok(ApiResponse.success("API permission deleted successfully", null));
    }

    @PostMapping("/api/check")
    public ResponseEntity<ApiResponse<Boolean>> checkApiPermission(@RequestBody Map<String, Object> request) {
        boolean hasPermission = permissionService.checkApiPermission(
                (String) request.get("contentTypeApiId"),
                (String) request.get("endpoint"),
                (String) request.get("method"),
                (List<String>) request.get("userRoles")
        );
        return ResponseEntity.ok(ApiResponse.success(hasPermission));
    }

    // Content Permission endpoints
    @PostMapping("/content")
    public ResponseEntity<ApiResponse<ContentPermissionDto>> createContentPermission(@RequestBody ContentPermissionDto dto) {
        ContentPermissionDto created = permissionService.createContentPermission(dto);
        return ResponseEntity.ok(ApiResponse.success("Content permission created successfully", created));
    }

    @GetMapping("/content")
    public ResponseEntity<ApiResponse<List<ContentPermissionDto>>> getAllContentPermissions() {
        List<ContentPermissionDto> permissions = permissionService.getAllContentPermissions();
        return ResponseEntity.ok(ApiResponse.success(permissions));
    }

    @GetMapping("/content/content-type/{contentTypeApiId}")
    public ResponseEntity<ApiResponse<List<ContentPermissionDto>>> getContentPermissionsByContentType(
            @PathVariable String contentTypeApiId) {
        List<ContentPermissionDto> permissions = permissionService.getContentPermissionsByContentType(contentTypeApiId);
        return ResponseEntity.ok(ApiResponse.success(permissions));
    }

    @GetMapping("/content/{id}")
    public ResponseEntity<ApiResponse<ContentPermissionDto>> getContentPermissionById(@PathVariable Long id) {
        ContentPermissionDto permission = permissionService.getContentPermissionById(id);
        return ResponseEntity.ok(ApiResponse.success(permission));
    }

    @PutMapping("/content/{id}")
    public ResponseEntity<ApiResponse<ContentPermissionDto>> updateContentPermission(
            @PathVariable Long id,
            @RequestBody ContentPermissionDto dto) {
        ContentPermissionDto updated = permissionService.updateContentPermission(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Content permission updated successfully", updated));
    }

    @DeleteMapping("/content/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteContentPermission(@PathVariable Long id) {
        permissionService.deleteContentPermission(id);
        return ResponseEntity.ok(ApiResponse.success("Content permission deleted successfully", null));
    }

    @PostMapping("/content/check")
    public ResponseEntity<ApiResponse<Boolean>> checkContentPermission(@RequestBody Map<String, Object> request) {
        boolean hasPermission = permissionService.checkContentPermission(
                (String) request.get("contentTypeApiId"),
                (String) request.get("action"),
                (List<String>) request.get("userRoles")
        );
        return ResponseEntity.ok(ApiResponse.success(hasPermission));
    }
}