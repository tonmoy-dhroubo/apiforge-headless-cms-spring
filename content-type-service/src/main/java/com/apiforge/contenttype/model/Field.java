package com.apiforge.contenttype.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "fields")
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String fieldName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FieldType type;

    private Boolean required;

    private Boolean unique;

    private String targetContentType;
    private String relationType;

    @ManyToOne
    @JoinColumn(name = "content_type_id")
    @JsonIgnore
    private ContentType contentType;

    public Field() {
    }

    public Field(Long id, String name, String fieldName, FieldType type, Boolean required, Boolean unique, String targetContentType, String relationType, ContentType contentType) {
        this.id = id;
        this.name = name;
        this.fieldName = fieldName;
        this.type = type;
        this.required = required;
        this.unique = unique;
        this.targetContentType = targetContentType;
        this.relationType = relationType;
        this.contentType = contentType;
    }

    public static FieldBuilder builder() {
        return new FieldBuilder();
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
    public ContentType getContentType() { return contentType; }
    public void setContentType(ContentType contentType) { this.contentType = contentType; }

    public static class FieldBuilder {
        private Long id;
        private String name;
        private String fieldName;
        private FieldType type;
        private Boolean required;
        private Boolean unique;
        private String targetContentType;
        private String relationType;
        private ContentType contentType;

        public FieldBuilder id(Long id) { this.id = id; return this; }
        public FieldBuilder name(String name) { this.name = name; return this; }
        public FieldBuilder fieldName(String fieldName) { this.fieldName = fieldName; return this; }
        public FieldBuilder type(FieldType type) { this.type = type; return this; }
        public FieldBuilder required(Boolean required) { this.required = required; return this; }
        public FieldBuilder unique(Boolean unique) { this.unique = unique; return this; }
        public FieldBuilder targetContentType(String targetContentType) { this.targetContentType = targetContentType; return this; }
        public FieldBuilder relationType(String relationType) { this.relationType = relationType; return this; }
        public FieldBuilder contentType(ContentType contentType) { this.contentType = contentType; return this; }

        public Field build() {
            return new Field(id, name, fieldName, type, required, unique, targetContentType, relationType, contentType);
        }
    }
}
