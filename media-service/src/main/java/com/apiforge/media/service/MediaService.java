package com.apiforge.media.service;

import com.apiforge.media.model.Media;
import com.apiforge.media.repository.MediaRepository;
import com.apiforge.common.exception.CustomExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    private final Path fileStorageLocation;

    @Autowired
    public MediaService(@Value("${file.upload-dir}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public Media storeFile(MultipartFile file) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = "";
        
        int i = originalFileName.lastIndexOf('.');
        if (i > 0) {
            extension = originalFileName.substring(i);
        }

        String hash = UUID.randomUUID().toString();
        String fileName = hash + extension;
        String storedName = resolveUniqueName(originalFileName);

        try {
            if (originalFileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + originalFileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Media media = Media.builder()
                .name(storedName)
                .hash(hash)
                .ext(extension)
                .mime(file.getContentType())
                .size((double) file.getSize() / 1024) // KB
                .url("/api/upload/files/" + fileName)
                .provider("local")
                .build();
            
            return mediaRepository.save(media);
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new CustomExceptions.ResourceNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new CustomExceptions.ResourceNotFoundException("File not found " + fileName);
        }
    }

    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    public Media getMediaById(Long id) {
        return mediaRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.ResourceNotFoundException("Media not found"));
    }

    public Media findByFilename(String filename) {
        String extension = "";
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            extension = filename.substring(i);
        }
        String hash = filename.substring(0, filename.length() - extension.length());
        return mediaRepository.findByHashAndExt(hash, extension).orElse(null);
    }

    public void deleteMedia(Long id) {
        Media media = getMediaById(id);
        
        // Delete file from storage
        String fileName = media.getHash() + media.getExt();
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            // Log warning
        }

        mediaRepository.delete(media);
    }

    private String resolveUniqueName(String originalFileName) {
        if (originalFileName == null || originalFileName.isBlank()) {
            return "upload";
        }

        String base = originalFileName;
        String extension = "";
        int i = originalFileName.lastIndexOf('.');
        if (i > 0) {
            base = originalFileName.substring(0, i);
            extension = originalFileName.substring(i);
        }

        String candidate = base + extension;
        int counter = 1;
        while (mediaRepository.existsByName(candidate)) {
            candidate = base + "-" + counter + extension;
            counter++;
        }
        return candidate;
    }
}
