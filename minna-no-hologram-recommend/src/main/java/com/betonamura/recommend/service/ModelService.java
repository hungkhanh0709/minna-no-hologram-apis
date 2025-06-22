package com.betonamura.recommend.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betonamura.recommend.config.RecommendationProperties;
import com.betonamura.recommend.domain.video.VideoMetadata;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for interacting with the Small Language Model (SLM).
 * This service handles model loading, embedding generation, and similarity
 * calculations.
 */
@Service
@Slf4j
public class ModelService {
    private final RecommendationProperties properties;
    private final ModelProvider modelProvider;

    @Autowired
    public ModelService(RecommendationProperties properties, ModelProvider modelProvider) {
        this.properties = properties;
        this.modelProvider = modelProvider;
    }

    @PostConstruct
    public void init() {
        try {
            // Create model directory if it doesn't exist
            Path modelDir = Paths.get(properties.getModel().getCacheDir());
            if (!Files.exists(modelDir)) {
                Files.createDirectories(modelDir);
                log.info("Created model directory: {}", modelDir);
            }

            // Initialize the model via the provider
            modelProvider.initialize();

            log.info("Model initialized successfully: {}", modelProvider.getModelName());
        } catch (IOException e) {
            log.error("Failed to initialize model directory", e);
            throw new RuntimeException("Model initialization failed", e);
        }
    }

    /**
     * Generate embeddings for content text
     *
     * @param text The text to generate embeddings for
     * @return A vector of float values representing the text embedding
     */
    public float[] generateEmbedding(String text) {
        log.debug("Generating embedding for text length: {}", text.length());
        return modelProvider.generateEmbedding(text);
    }

    /**
     * Calculate cosine similarity between two embedding vectors
     *
     * @param embedding1 First embedding vector
     * @param embedding2 Second embedding vector
     * @return Similarity score between 0 and 1
     */
    public double calculateSimilarity(float[] embedding1, float[] embedding2) {
        if (embedding1.length != embedding2.length) {
            throw new IllegalArgumentException("Embedding dimensions do not match");
        }

        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (int i = 0; i < embedding1.length; i++) {
            dotProduct += embedding1[i] * embedding2[i];
            norm1 += embedding1[i] * embedding1[i];
            norm2 += embedding2[i] * embedding2[i];
        }

        // Prevent division by zero
        if (norm1 <= 0.0 || norm2 <= 0.0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }

    /**
     * Generate content description for embedding generation
     * 
     * @param videoMetadata The video metadata
     * @return A string representation suitable for embedding
     */
    public String generateContentDescription(VideoMetadata videoMetadata) {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(videoMetadata.getTitle()).append(". ");

        if (videoMetadata.getDescription() != null && !videoMetadata.getDescription().isEmpty()) {
            sb.append("Description: ").append(videoMetadata.getDescription()).append(". ");
        }

        if (videoMetadata.getTags() != null && !videoMetadata.getTags().isEmpty()) {
            sb.append("Tags: ").append(videoMetadata.getTags().replace(",", ", ")).append(".");
        }

        return sb.toString();
    }

    /**
     * Generate a user profile description based on watch history
     * 
     * @param userProfile Map containing user profile data
     * @return A string representation suitable for embedding
     */
    public String generateUserProfileDescription(Map<String, Object> userProfile) {
        StringBuilder sb = new StringBuilder();
        sb.append("User preferences: ");

        @SuppressWarnings("unchecked")
        List<String> categories = (List<String>) userProfile.getOrDefault("preferredCategories", List.of());
        if (!categories.isEmpty()) {
            sb.append("Categories: ").append(String.join(", ", categories)).append(". ");
        }

        @SuppressWarnings("unchecked")
        List<String> tags = (List<String>) userProfile.getOrDefault("preferredTags", List.of());
        if (!tags.isEmpty()) {
            sb.append("Tags: ").append(String.join(", ", tags)).append(".");
        }

        return sb.toString();
    }
}
