package com.apiforge.contenttype.controller;

import com.apiforge.contenttype.dto.ContentTypeDto;
import com.apiforge.contenttype.service.ContentTypeService;
import com.apiforge.common.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/content-types")
public class ContentTypeController {

    @Autowired
    private ContentTypeService contentTypeService;

    @PostMapping
    public ResponseEntity<ApiResponse<ContentTypeDto>> createContentType(@RequestBody ContentTypeDto dto) {
        ContentTypeDto created = contentTypeService.createContentType(dto);
        return ResponseEntity.ok(ApiResponse.success("Content type created successfully", created));
    }

    @GetMapping
    @Operation(
            summary = "List content types",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Seeded content types",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "SeededContentTypes",
                                    value = """
                                            {
                                              "success": true,
                                              "message": null,
                                              "data": [
                                                {
                                                  "id": 1,
                                                  "name": "Author",
                                                  "pluralName": "Authors",
                                                  "apiId": "author",
                                                  "description": "Writer profiles for editorial content",
                                                  "fields": [
                                                    {
                                                      "id": 1,
                                                      "name": "Name",
                                                      "fieldName": "name",
                                                      "type": "SHORT_TEXT",
                                                      "required": true,
                                                      "unique": false,
                                                      "targetContentType": null,
                                                      "relationType": null
                                                    },
                                                    {
                                                      "id": 2,
                                                      "name": "Bio",
                                                      "fieldName": "bio",
                                                      "type": "LONG_TEXT",
                                                      "required": false,
                                                      "unique": false,
                                                      "targetContentType": null,
                                                      "relationType": null
                                                    },
                                                    {
                                                      "id": 3,
                                                      "name": "Email",
                                                      "fieldName": "email",
                                                      "type": "SHORT_TEXT",
                                                      "required": false,
                                                      "unique": true,
                                                      "targetContentType": null,
                                                      "relationType": null
                                                    },
                                                    {
                                                      "id": 4,
                                                      "name": "Avatar",
                                                      "fieldName": "avatar",
                                                      "type": "MEDIA",
                                                      "required": false,
                                                      "unique": false,
                                                      "targetContentType": null,
                                                      "relationType": null
                                                    }
                                                  ]
                                                }
                                              ],
                                              "error": null
                                            }
                                            """
                            )
                    )
            )
    )
    public ResponseEntity<ApiResponse<List<ContentTypeDto>>> getAllContentTypes() {
        List<ContentTypeDto> contentTypes = contentTypeService.getAllContentTypes();
        return ResponseEntity.ok(ApiResponse.success(contentTypes));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ContentTypeDto>> getContentTypeById(@PathVariable Long id) {
        ContentTypeDto contentType = contentTypeService.getContentTypeById(id);
        return ResponseEntity.ok(ApiResponse.success(contentType));
    }

    @GetMapping("/api-id/{apiId}")
    public ResponseEntity<ApiResponse<ContentTypeDto>> getContentTypeByApiId(@PathVariable String apiId) {
        ContentTypeDto contentType = contentTypeService.getContentTypeByApiId(apiId);
        return ResponseEntity.ok(ApiResponse.success(contentType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ContentTypeDto>> updateContentType(
            @PathVariable Long id,
            @RequestBody ContentTypeDto dto) {
        ContentTypeDto updated = contentTypeService.updateContentType(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Content type updated successfully", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteContentType(@PathVariable Long id) {
        contentTypeService.deleteContentType(id);
        return ResponseEntity.ok(ApiResponse.success("Content type deleted successfully", null));
    }
}
