package com.apiforge.content.controller;

import com.apiforge.content.service.ContentService;
import com.apiforge.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/content/{apiId}")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> createContent(
            @PathVariable String apiId,
            @RequestBody Map<String, Object> data) {
        Map<String, Object> created = contentService.createContent(apiId, data);
        return ResponseEntity.ok(ApiResponse.success("Content created successfully", created));
    }

    @GetMapping
    @Operation(
            summary = "List content entries by content type",
            description = "Example uses seeded Author entries (apiId=author).",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Seeded content for apiId=author",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "SeededAuthors",
                                    value = """
                                            {
                                              "success": true,
                                              "message": null,
                                              "data": [
                                                {
                                                  "id": 1,
                                                  "name": "Jane Doe",
                                                  "bio": "Tech journalist focusing on developer tools.",
                                                  "email": "jane.doe@apiforge.com",
                                                  "avatar": 2
                                                },
                                                {
                                                  "id": 2,
                                                  "name": "John Smith",
                                                  "bio": "Product writer covering hardware and design.",
                                                  "email": "john.smith@apiforge.com",
                                                  "avatar": 3
                                                }
                                              ],
                                              "error": null
                                            }
                                            """
                            )
                    )
            )
    )
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getAllContent(@PathVariable String apiId) {
        List<Map<String, Object>> contents = contentService.getAllContent(apiId);
        return ResponseEntity.ok(ApiResponse.success(contents));
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> searchContent(
            @PathVariable String apiId,
            @RequestBody Map<String, Object> filters) {
        List<Map<String, Object>> results = contentService.searchContent(apiId, filters);
        return ResponseEntity.ok(ApiResponse.success(results));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getContentById(
            @PathVariable String apiId,
            @PathVariable Long id) {
        Map<String, Object> content = contentService.getContentById(apiId, id);
        return ResponseEntity.ok(ApiResponse.success(content));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateContent(
            @PathVariable String apiId,
            @PathVariable Long id,
            @RequestBody Map<String, Object> data) {
        Map<String, Object> updated = contentService.updateContent(apiId, id, data);
        return ResponseEntity.ok(ApiResponse.success("Content updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteContent(
            @PathVariable String apiId,
            @PathVariable Long id) {
        contentService.deleteContent(apiId, id);
        return ResponseEntity.ok(ApiResponse.success("Content deleted successfully", null));
    }
}
