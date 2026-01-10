package com.apiforge.contenttype.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "content_types")
public class ContentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String pluralName;

    @Column(unique = true, nullable = false)
    private String apiId;

    private String description;

    @OneToMany(mappedBy = "contentType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Field> fields = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public ContentType() {
    }

    public ContentType(Long id, String name, String pluralName, String apiId, String description, List<Field> fields, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.pluralName = pluralName;
        this.apiId = apiId;
        this.description = description;
        if (fields != null) this.fields = fields;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ContentTypeBuilder builder() {
        return new ContentTypeBuilder();
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
    public List<Field> getFields() { return fields; }
    public void setFields(List<Field> fields) { this.fields = fields; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static class ContentTypeBuilder {
        private Long id;
        private String name;
        private String pluralName;
        private String apiId;
        private String description;
        private List<Field> fields = new ArrayList<>();
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public ContentTypeBuilder id(Long id) { this.id = id; return this; }
        public ContentTypeBuilder name(String name) { this.name = name; return this; }
        public ContentTypeBuilder pluralName(String pluralName) { this.pluralName = pluralName; return this; }
        public ContentTypeBuilder apiId(String apiId) { this.apiId = apiId; return this; }
        public ContentTypeBuilder description(String description) { this.description = description; return this; }
        public ContentTypeBuilder fields(List<Field> fields) { this.fields = fields; return this; }
        public ContentTypeBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public ContentTypeBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public ContentType build() {
            return new ContentType(id, name, pluralName, apiId, description, fields, createdAt, updatedAt);
        }
    }
}
