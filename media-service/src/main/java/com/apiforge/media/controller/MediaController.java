package com.apiforge.media.controller;

import com.apiforge.media.model.Media;
import com.apiforge.media.service.MediaService;
import com.apiforge.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @PostMapping
    public ResponseEntity<ApiResponse<Media>> uploadFile(@RequestParam("files") MultipartFile file) {
        Media media = mediaService.storeFile(file);
        return ResponseEntity.ok(ApiResponse.success("File uploaded successfully", media));
    }

    @GetMapping
    @Operation(
            summary = "List media",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Seeded media entries",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "SeededMedia",
                                    value = """
                                            {
                                              "success": true,
                                              "message": null,
                                              "data": [
                                                {
                                                  "id": 1,
                                                  "name": "hero-mountains",
                                                  "alternativeText": "Mountain landscape",
                                                  "caption": "Snowy mountain range",
                                                  "width": 1920,
                                                  "height": 1080,
                                                  "hash": "hash_mountains_01",
                                                  "ext": ".jpg",
                                                  "mime": "image/jpeg",
                                                  "size": 1.82,
                                                  "url": "https://cdn.apiforge.local/media/hero-mountains.jpg",
                                                  "provider": "local"
                                                },
                                                {
                                                  "id": 2,
                                                  "name": "author-jane",
                                                  "alternativeText": "Portrait of Jane Doe",
                                                  "caption": "Author portrait",
                                                  "width": 800,
                                                  "height": 800,
                                                  "hash": "hash_author_jane",
                                                  "ext": ".jpg",
                                                  "mime": "image/jpeg",
                                                  "size": 0.64,
                                                  "url": "https://cdn.apiforge.local/media/author-jane.jpg",
                                                  "provider": "local"
                                                }
                                              ],
                                              "error": null
                                            }
                                            """
                            )
                    )
            )
    )
    public ResponseEntity<ApiResponse<List<Media>>> getAllMedia() {
        List<Media> mediaList = mediaService.getAllMedia();
        return ResponseEntity.ok(ApiResponse.success(mediaList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Media>> getMediaById(@PathVariable Long id) {
        Media media = mediaService.getMediaById(id);
        return ResponseEntity.ok(ApiResponse.success(media));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteMedia(@PathVariable Long id) {
        mediaService.deleteMedia(id);
        return ResponseEntity.ok(ApiResponse.success("File deleted successfully", null));
    }

    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = mediaService.loadFileAsResource(fileName);
        Media media = mediaService.findByFilename(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            // logger.info("Could not determine file type.");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        if (media != null && media.getName() != null) {
            String safeName = media.getName().replace("\"", "");
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + safeName + "\"; filename*=UTF-8''" +
                                    java.net.URLEncoder.encode(media.getName(), java.nio.charset.StandardCharsets.UTF_8))
                    .body(resource);
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
