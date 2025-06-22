package com.betonamura.recommend.domain.embedding;

import lombok.Builder;
import lombok.Data;

/**
 * Represents an embedding vector for content (video, DIY, etc.)
 * These embeddings are used to calculate similarity between items.
 */
@Data
@Builder
public class ContentEmbedding {
    private String contentId;
    private String contentType; // "video" or "diy"
    private float[] vector;
    private long timestamp; // when this embedding was created

    /**
     * Calculate cosine similarity with another embedding
     * 
     * @param other The other embedding to compare with
     * @return Similarity score between 0 and 1
     */
    public double calculateSimilarity(ContentEmbedding other) {
        if (this.vector.length != other.vector.length) {
            throw new IllegalArgumentException("Embedding dimensions don't match");
        }

        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (int i = 0; i < this.vector.length; i++) {
            dotProduct += this.vector[i] * other.vector[i];
            norm1 += this.vector[i] * this.vector[i];
            norm2 += other.vector[i] * other.vector[i];
        }

        // Prevent division by zero
        if (norm1 <= 0.0 || norm2 <= 0.0) {
            return 0.0;
        }

        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
}
