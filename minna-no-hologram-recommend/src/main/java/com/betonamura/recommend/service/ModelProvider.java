package com.betonamura.recommend.service;

/**
 * Interface for different model providers (HuggingFace, OpenAI, etc.)
 */
public interface ModelProvider {
    /**
     * Initialize the model
     */
    void initialize();

    /**
     * Generate text embeddings
     * 
     * @param text Input text
     * @return Embedding vector
     */
    float[] generateEmbedding(String text);

    /**
     * Get the model name
     */
    String getModelName();

    /**
     * Get the embedding dimensions
     */
    int getEmbeddingDimension();
}
