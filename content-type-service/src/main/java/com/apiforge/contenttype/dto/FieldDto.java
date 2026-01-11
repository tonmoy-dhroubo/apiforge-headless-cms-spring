package com.apiforge.contenttype.dto;

import com.apiforge.contenttype.model.FieldType;

public class FieldDto {
    private Long id;
    private String name;
    private String fieldName;
    private FieldType type;
    private Boolean required;
    private Boolean unique;
    private String targetContentType;
    private String relationType;

    public FieldDto() {
    }

    public FieldDto(Long id, String name, String fieldName, FieldType type, Boolean required, Boolean unique, String targetContentType, String relationType) {
        this.id = id;
        this.name = name;
        this.fieldName = fieldName;
        this.type = type;
        this.required = required;
        this.unique = unique;
        this.targetContentType = targetContentType;
        this.relationType = relationType;
    }

    public static FieldDtoBuilder builder() {
        return new FieldDtoBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getFieldName() { return fieldName; }
    public void setFieldName(String fieldName) { this.fieldName = fieldName; }
    public FieldType getType() { return type; }
    public void setType(FieldType type) { this.type = type; }
    public Boolean getRequired() { return required; }
    public void setRequired(Boolean required) { this.required = required; }
    public Boolean getUnique() { return unique; }
    public void setUnique(Boolean unique) { this.unique = unique; }
    public String getTargetContentType() { return targetContentType; }
    public void setTargetContentType(String targetContentType) { this.targetContentType = targetContentType; }
    public String getRelationType() { return relationType; }
    public void setRelationType(String relationType) { this.relationType = relationType; }

    public static class FieldDtoBuilder {
        private Long id;
        private String name;
        private String fieldName;
        private FieldType type;
        private Boolean required;
        private Boolean unique;
        private String targetContentType;
        private String relationType;

        public FieldDtoBuilder id(Long id) { this.id = id; return this; }
        public FieldDtoBuilder name(String name) { this.name = name; return this; }
        public FieldDtoBuilder fieldName(String fieldName) { this.fieldName = fieldName; return this; }
        public FieldDtoBuilder type(FieldType type) { this.type = type; return this; }
        public FieldDtoBuilder required(Boolean required) { this.required = required; return this; }
        public FieldDtoBuilder unique(Boolean unique) { this.unique = unique; return this; }
        public FieldDtoBuilder targetContentType(String targetContentType) { this.targetContentType = targetContentType; return this; }
        public FieldDtoBuilder relationType(String relationType) { this.relationType = relationType; return this; }

        public FieldDto build() {
            return new FieldDto(id, name, fieldName, type, required, unique, targetContentType, relationType);
        }
    }
}
