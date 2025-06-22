package com.betonamura.recommend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "recommendation")
public class RecommendationProperties {
    private ModelConfig model = new ModelConfig();
    private Integer defaultLimit = 10;
    private Double minSimilarityScore = 0.5;
    private Boolean precomputeEmbeddings = true;
    private CacheConfig cache = new CacheConfig();

    @Data
    public static class ModelConfig {
        private String name = "bert-base-uncased";
        private String path = "models";
        private String cacheDir = "${user.home}/.hologram-recommend/models";
        private Integer embeddingDimension = 768;
        private Integer maxLength = 128;
        private Integer batchSize = 16;
        private Boolean enabled = true;
        private String provider = "huggingface"; // or "openai", "tensorflow", etc.
        private String apiKey = ""; // For API based models like OpenAI
    }

    @Data
    public static class CacheConfig {
        private Integer ttlMinutes = 30;
        private Integer maxSize = 1000;
        private Boolean enabled = true;
    }
}
