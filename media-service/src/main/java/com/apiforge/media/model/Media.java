package com.apiforge.media.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "media")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String alternativeText;
    private String caption;
    private Integer width;
    private Integer height;
    
    @Column(nullable = false)
    private String hash;
    
    private String ext;
    private String mime;
    private Double size;
    private String url;
    private String provider;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Media() {}

    public Media(Long id, String name, String alternativeText, String caption, Integer width, Integer height, String hash, String ext, String mime, Double size, String url, String provider, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.alternativeText = alternativeText;
        this.caption = caption;
        this.width = width;
        this.height = height;
        this.hash = hash;
        this.ext = ext;
        this.mime = mime;
        this.size = size;
        this.url = url;
        this.provider = provider;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MediaBuilder builder() {
        return new MediaBuilder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAlternativeText() { return alternativeText; }
    public void setAlternativeText(String alternativeText) { this.alternativeText = alternativeText; }
    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }
    public Integer getWidth() { return width; }
    public void setWidth(Integer width) { this.width = width; }
    public Integer getHeight() { return height; }
    public void setHeight(Integer height) { this.height = height; }
    public String getHash() { return hash; }
    public void setHash(String hash) { this.hash = hash; }
    public String getExt() { return ext; }
    public void setExt(String ext) { this.ext = ext; }
    public String getMime() { return mime; }
    public void setMime(String mime) { this.mime = mime; }
    public Double getSize() { return size; }
    public void setSize(Double size) { this.size = size; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public static class MediaBuilder {
        private Long id;
        private String name;
        private String alternativeText;
        private String caption;
        private Integer width;
        private Integer height;
        private String hash;
        private String ext;
        private String mime;
        private Double size;
        private String url;
        private String provider;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public MediaBuilder id(Long id) { this.id = id; return this; }
        public MediaBuilder name(String name) { this.name = name; return this; }
        public MediaBuilder alternativeText(String alternativeText) { this.alternativeText = alternativeText; return this; }
        public MediaBuilder caption(String caption) { this.caption = caption; return this; }
        public MediaBuilder width(Integer width) { this.width = width; return this; }
        public MediaBuilder height(Integer height) { this.height = height; return this; }
        public MediaBuilder hash(String hash) { this.hash = hash; return this; }
        public MediaBuilder ext(String ext) { this.ext = ext; return this; }
        public MediaBuilder mime(String mime) { this.mime = mime; return this; }
        public MediaBuilder size(Double size) { this.size = size; return this; }
        public MediaBuilder url(String url) { this.url = url; return this; }
        public MediaBuilder provider(String provider) { this.provider = provider; return this; }
        public MediaBuilder createdAt(LocalDateTime createdAt) { this.createdAt = createdAt; return this; }
        public MediaBuilder updatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; return this; }

        public Media build() {
            return new Media(id, name, alternativeText, caption, width, height, hash, ext, mime, size, url, provider, createdAt, updatedAt);
        }
    }
}
