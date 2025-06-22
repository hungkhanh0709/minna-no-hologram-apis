package com.betonamura.recommend.service;

import com.betonamura.recommend.config.RecommendationProperties;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * A mock implementation of ModelProvider for development and testing
 */
@Slf4j
public class MockModelProvider implements ModelProvider {
    private final RecommendationProperties properties;

    public MockModelProvider(RecommendationProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    @Override
    public void initialize() {
        log.info("Initializing mock model provider with model: {}", properties.getModel().getName());
    }

    @Override
    public float[] generateEmbedding(String text) {
        int dimension = properties.getModel().getEmbeddingDimension();
        float[] embedding = new float[dimension];

        // Generate deterministic pseudo-embeddings based on text hash
        int hash = text.hashCode();
        for (int i = 0; i < dimension; i++) {
            embedding[i] = (float) Math.sin(i * 0.1 + hash * 0.01);
        }

        return embedding;
    }

    @Override
    public String getModelName() {
        return "mock-" + properties.getModel().getName();
    }

    @Override
    public int getEmbeddingDimension() {
        return properties.getModel().getEmbeddingDimension();
    }
}
