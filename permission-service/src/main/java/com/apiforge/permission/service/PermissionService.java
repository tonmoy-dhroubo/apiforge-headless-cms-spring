package com.apiforge.permission.service;

import com.apiforge.common.exception.CustomExceptions;
import com.apiforge.permission.dto.ApiPermissionDto;
import com.apiforge.permission.dto.ContentPermissionDto;
import com.apiforge.permission.model.ApiPermission;
import com.apiforge.permission.model.ContentPermission;
import com.apiforge.permission.repository.ApiPermissionRepository;
import com.apiforge.permission.repository.ContentPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    @Autowired
    private ApiPermissionRepository apiPermissionRepository;

    @Autowired
    private ContentPermissionRepository contentPermissionRepository;

    // API Permission methods
    public ApiPermissionDto createApiPermission(ApiPermissionDto dto) {
        ApiPermission permission = ApiPermission.builder()
                .contentTypeApiId(dto.getContentTypeApiId())
                .endpoint(dto.getEndpoint())
                .method(dto.getMethod())
                .allowedRoles(dto.getAllowedRoles())
                .build();

        permission = apiPermissionRepository.save(permission);
        return convertApiPermissionToDto(permission);
    }

    public List<ApiPermissionDto> getAllApiPermissions() {
        return apiPermissionRepository.findAll().stream()
                .map(this::convertApiPermissionToDto)
                .collect(Collectors.toList());
    }

    public List<ApiPermissionDto> getApiPermissionsByContentType(String contentTypeApiId) {
        return apiPermissionRepository.findByContentTypeApiId(contentTypeApiId).stream()
                .map(this::convertApiPermissionToDto)
                .collect(Collectors.toList());
    }

    public ApiPermissionDto getApiPermissionById(Long id) {
        ApiPermission permission = apiPermissionRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("API permission not found"));
        return convertApiPermissionToDto(permission);
    }

    public ApiPermissionDto updateApiPermission(Long id, ApiPermissionDto dto) {
        ApiPermission permission = apiPermissionRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("API permission not found"));

        permission.setAllowedRoles(dto.getAllowedRoles());
        permission = apiPermissionRepository.save(permission);

        return convertApiPermissionToDto(permission);
    }

    public void deleteApiPermission(Long id) {
        if (!apiPermissionRepository.existsById(id)) {
            throw new CustomExceptions.ResourceNotFoundException("API permission not found");
        }
        apiPermissionRepository.deleteById(id);
    }

    public boolean checkApiPermission(String contentTypeApiId, String endpoint, String method, List<String> userRoles) {
        ApiPermission permission = apiPermissionRepository
                .findByContentTypeApiIdAndEndpointAndMethod(contentTypeApiId, endpoint, method)
                .orElse(null);

        // If no explicit permission is set, deny by default (secure by default)
        // Alternatively, you can allow by default if that's the preferred strategy
        if (permission == null) {
            return false; 
        }

        return permission.getAllowedRoles().stream()
                .anyMatch(userRoles::contains);
    }

    // Content Permission methods
    public ContentPermissionDto createContentPermission(ContentPermissionDto dto) {
        ContentPermission permission = ContentPermission.builder()
                .contentTypeApiId(dto.getContentTypeApiId())
                .action(dto.getAction())
                .allowedRoles(dto.getAllowedRoles())
                .build();

        permission = contentPermissionRepository.save(permission);
        return convertContentPermissionToDto(permission);
    }

    public List<ContentPermissionDto> getAllContentPermissions() {
        return contentPermissionRepository.findAll().stream()
                .map(this::convertContentPermissionToDto)
                .collect(Collectors.toList());
    }

    public List<ContentPermissionDto> getContentPermissionsByContentType(String contentTypeApiId) {
        return contentPermissionRepository.findByContentTypeApiId(contentTypeApiId).stream()
                .map(this::convertContentPermissionToDto)
                .collect(Collectors.toList());
    }

    public ContentPermissionDto getContentPermissionById(Long id) {
        ContentPermission permission = contentPermissionRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Content permission not found"));
        return convertContentPermissionToDto(permission);
    }

    public ContentPermissionDto updateContentPermission(Long id, ContentPermissionDto dto) {
        ContentPermission permission = contentPermissionRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Content permission not found"));

        permission.setAllowedRoles(dto.getAllowedRoles());
        permission = contentPermissionRepository.save(permission);

        return convertContentPermissionToDto(permission);
    }

    public void deleteContentPermission(Long id) {
        if (!contentPermissionRepository.existsById(id)) {
            throw new CustomExceptions.ResourceNotFoundException("Content permission not found");
        }
        contentPermissionRepository.deleteById(id);
    }

    public boolean checkContentPermission(String contentTypeApiId, String action, List<String> userRoles) {
        ContentPermission permission = contentPermissionRepository
                .findByContentTypeApiIdAndAction(contentTypeApiId, action)
                .orElse(null);

        if (permission == null) {
            return false;
        }

        return permission.getAllowedRoles().stream()
                .anyMatch(userRoles::contains);
    }

    // Conversion methods
    private ApiPermissionDto convertApiPermissionToDto(ApiPermission permission) {
        return ApiPermissionDto.builder()
                .id(permission.getId())
                .contentTypeApiId(permission.getContentTypeApiId())
                .endpoint(permission.getEndpoint())
                .method(permission.getMethod())
                .allowedRoles(permission.getAllowedRoles())
                .createdAt(permission.getCreatedAt())
                .build();
    }

    private ContentPermissionDto convertContentPermissionToDto(ContentPermission permission) {
        return ContentPermissionDto.builder()
                .id(permission.getId())
                .contentTypeApiId(permission.getContentTypeApiId())
                .action(permission.getAction())
                .allowedRoles(permission.getAllowedRoles())
                .createdAt(permission.getCreatedAt())
                .build();
    }
}