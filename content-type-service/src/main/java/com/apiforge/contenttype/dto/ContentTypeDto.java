package com.apiforge.contenttype.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ContentTypeDto {
    private Long id;
    private String name;
    private String pluralName;
    private String apiId;
    private String description;
    private List<FieldDto> fields;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ContentTypeDto() {
    }

    public ContentTypeDto(Long id, String name, String pluralName, String apiId, String description, List<FieldDto> fields, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.pluralName = pluralName;
        this.apiId = apiId;
        this.description = description;
        this.fields = fields;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ContentTypeDtoBuilder builder() {
        return new ContentTypeDtoBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPluralName() { return pluralName; }
    public void setPluralName(String pluralName) { this.pluralName = pluralName; }
    public String getApiId() { return apiId; }
    public void setApiId(String apiId) { this.apiId = apiId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<FieldDto> getFields() { return fields; }
    public void setFields(List<FieldDto> fields) { this.fields = fields; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static class ContentTypeDtoBuilder {
        private Long id;
        private String name;
        private String pluralName;
        private String apiId;
        private String description;
        private List<FieldDto> fields;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public ContentTypeDtoBuilder id(Long id) { this.id = id; return this; }
        public ContentTypeDtoBuilder name(String name) { this.name = name; return this; }
        public ContentTypeDtoBuilder pluralName(String pluralName) { this.pluralName = pluralName; return this; }
        public ContentTypeDtoBuilder apiId(String apiId) { this.apiId = apiId; return this; }
        public ContentTypeDtoBuilder description(String description) { this.description = description; return this; }
        public ContentTypeDtoBuilder fields(List<FieldDto> fields) { this.fields = fields; return this; }
        public ContentTypeDtoBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public ContentTypeDtoBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public ContentTypeDto build() {
            return new ContentTypeDto(id, name, pluralName, apiId, description, fields, createdAt, updatedAt);
        }
    }
}
