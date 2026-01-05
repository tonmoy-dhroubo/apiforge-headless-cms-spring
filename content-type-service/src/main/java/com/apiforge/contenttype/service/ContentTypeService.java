package com.apiforge.contenttype.service;

import com.apiforge.contenttype.dto.ContentTypeDto;
import com.apiforge.contenttype.dto.FieldDto;
import com.apiforge.contenttype.model.ContentType;
import com.apiforge.contenttype.model.Field;
import com.apiforge.contenttype.repository.ContentTypeRepository;
import com.apiforge.common.exception.CustomExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContentTypeService {

    @Autowired
    private ContentTypeRepository contentTypeRepository;

    @Autowired
    private DynamicTableService dynamicTableService;

    @Transactional
    public ContentTypeDto createContentType(ContentTypeDto dto) {
        if (contentTypeRepository.existsByApiId(dto.getApiId())) {
            throw new CustomExceptions.ConflictException("Content type with this API ID already exists");
        }

        ContentType contentType = ContentType.builder()
                .name(dto.getName())
                .pluralName(dto.getPluralName() != null ? dto.getPluralName() : dto.getName() + "s")
                .apiId(dto.getApiId())
                .description(dto.getDescription())
                .build();

        final ContentType finalContentType = contentType;
        if (dto.getFields() != null) {
            List<Field> fields = dto.getFields().stream()
                    .map(fieldDto -> {
                        Field field = Field.builder()
                                .name(fieldDto.getName())
                                .fieldName(fieldDto.getFieldName())
                                .type(fieldDto.getType())
                                .required(fieldDto.getRequired())
                                .unique(fieldDto.getUnique())
                                .targetContentType(fieldDto.getTargetContentType())
                                .relationType(fieldDto.getRelationType())
                                .contentType(finalContentType)
                                .build();
                        return field;
                    })
                    .collect(Collectors.toList());

            contentType.setFields(fields);
        }

        ContentType savedContentType = contentTypeRepository.save(contentType);

        // Create dynamic table
        dynamicTableService.createTableForContentType("ct_" + dto.getApiId(), savedContentType.getFields());

        return convertToDto(savedContentType);
    }

    public List<ContentTypeDto> getAllContentTypes() {
        return contentTypeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ContentTypeDto getContentTypeById(Long id) {
        ContentType contentType = contentTypeRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Content type not found"));
        return convertToDto(contentType);
    }

    public ContentTypeDto getContentTypeByApiId(String apiId) {
        ContentType contentType = contentTypeRepository.findByApiId(apiId)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Content type not found"));
        return convertToDto(contentType);
    }

    @Transactional
    public ContentTypeDto updateContentType(Long id, ContentTypeDto dto) {
        ContentType contentType = contentTypeRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Content type not found"));

        if (dto.getName() != null) {
            contentType.setName(dto.getName());
        }
        if (dto.getPluralName() != null) {
            contentType.setPluralName(dto.getPluralName());
        }
        if (dto.getDescription() != null) {
            contentType.setDescription(dto.getDescription());
        }

        // Handle field updates - this is a simplification, ideally should migrate schema
        if (dto.getFields() != null) {
            final ContentType finalContentType = contentType;
            List<Field> newFields = dto.getFields().stream()
                    .map(fieldDto -> {
                        Field field = Field.builder()
                                .id(fieldDto.getId())
                                .name(fieldDto.getName())
                                .fieldName(fieldDto.getFieldName())
                                .type(fieldDto.getType())
                                .required(fieldDto.getRequired())
                                .unique(fieldDto.getUnique())
                                .targetContentType(fieldDto.getTargetContentType())
                                .relationType(fieldDto.getRelationType())
                                .contentType(finalContentType)
                                .build();
                        return field;
                    })
                    .collect(Collectors.toList());

            contentType.getFields().clear();
            contentType.getFields().addAll(newFields);
        }

        ContentType savedContentType = contentTypeRepository.save(contentType);

        // Note: Schema migration for updates is complex and not fully implemented here
        // Ideally we should call dynamicTableService to add/remove columns

        return convertToDto(savedContentType);
    }

    @Transactional
    public void deleteContentType(Long id) {
        ContentType contentType = contentTypeRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Content type not found"));

        // Drop dynamic table
        dynamicTableService.dropTableForContentType("ct_" + contentType.getApiId());

        contentTypeRepository.delete(contentType);
    }

    private ContentTypeDto convertToDto(ContentType contentType) {
        List<FieldDto> fieldDtos = contentType.getFields().stream()
                .map(field -> FieldDto.builder()
                        .id(field.getId())
                        .name(field.getName())
                        .fieldName(field.getFieldName())
                        .type(field.getType())
                        .required(field.getRequired())
                        .unique(field.getUnique())
                        .targetContentType(field.getTargetContentType())
                        .relationType(field.getRelationType())
                        .build())
                .collect(Collectors.toList());

        return ContentTypeDto.builder()
                .id(contentType.getId())
                .name(contentType.getName())
                .pluralName(contentType.getPluralName())
                .apiId(contentType.getApiId())
                .description(contentType.getDescription())
                .fields(fieldDtos)
                .createdAt(contentType.getCreatedAt())
                .updatedAt(contentType.getUpdatedAt())
                .build();
    }
}
