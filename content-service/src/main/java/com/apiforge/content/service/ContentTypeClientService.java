package com.apiforge.content.service;

import com.apiforge.common.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Service
public class ContentTypeClientService {

    @Value("${content-type-service.url}")
    private String contentTypeServiceUrl;

    private final WebClient webClient;

    public ContentTypeClientService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Map<String, Object> getContentTypeByApiId(String apiId) {
        ApiResponse response = webClient.get()
                .uri(contentTypeServiceUrl + "/api/content-types/api-id/" + apiId)
                .retrieve()
                .bodyToMono(ApiResponse.class)
                .block();

        if (response != null && response.isSuccess()) {
             return (Map<String, Object>) response.getData();
        }
        
        return null;
    }
}
