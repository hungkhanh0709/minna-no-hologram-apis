package com.betonamura.recommend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "recommendation")
public class RecommendationProperties {
    private ModelConfig model = new ModelConfig();
    private Integer defaultLimit = 10;
    private CacheConfig cache = new CacheConfig();

    @Data
    public static class ModelConfig {
        private String name = "bert-base-uncased";
        private Integer maxLength = 128;
        private Integer batchSize = 16;
    }

    @Data
    public static class CacheConfig {
        private Integer ttlMinutes = 30;
        private Integer maxSize = 1000;
    }
}
