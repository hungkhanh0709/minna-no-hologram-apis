package com.betonamura.recommend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betonamura.recommend.config.ApiConfig;
import com.betonamura.recommend.config.EmbeddingCacheService;
import com.betonamura.recommend.config.RecommendationProperties;
import com.betonamura.recommend.service.ContentEmbeddingService;
import com.betonamura.recommend.service.ModelProvider;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller for displaying model information
 */
@Slf4j
@RequestMapping
@RestController
public class ModelController {

    private final ModelProvider modelProvider;
    private final RecommendationProperties properties;
    private final EmbeddingCacheService cacheService;
    private final ContentEmbeddingService embeddingService;

    @Autowired
    public ModelController(
            ModelProvider modelProvider,
            RecommendationProperties properties,
            EmbeddingCacheService cacheService,
            ContentEmbeddingService embeddingService) {
        this.modelProvider = modelProvider;
        this.properties = properties;
        this.cacheService = cacheService;
        this.embeddingService = embeddingService;
    }

    /**
     * Get model information
     */
    @GetMapping(ApiConfig.MODEL_INFO)
    public ResponseEntity<Map<String, Object>> getModelInfo() {
        Map<String, Object> info = new HashMap<>();

        info.put("modelName", modelProvider.getModelName());
        info.put("embeddingDimension", modelProvider.getEmbeddingDimension());
        info.put("cacheSize", cacheService.size());
        info.put("provider", properties.getModel().getProvider());
        info.put("minSimilarityScore", properties.getMinSimilarityScore());

        return ResponseEntity.ok(info);
    }

    /**
     * Trigger model retraining/embedding precomputation
     */
    @GetMapping(ApiConfig.MODEL_REFRESH)
    public ResponseEntity<?> refreshModel() {
        try {
            // Trigger asynchronous embedding precomputation
            embeddingService.precomputeAllEmbeddings();

            Map<String, String> response = Map.of(
                    "status", "success",
                    "message", "Embedding precomputation started");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error refreshing model", e);
            Map<String, String> error = Map.of(
                    "status", "error",
                    "message", "Failed to refresh model: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
