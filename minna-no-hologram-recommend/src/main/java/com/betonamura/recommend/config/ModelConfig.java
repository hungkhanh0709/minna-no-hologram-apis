package com.betonamura.recommend.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.betonamura.recommend.service.MockModelProvider;
import com.betonamura.recommend.service.ModelProvider;
import com.betonamura.recommend.service.ModelService;

import lombok.extern.slf4j.Slf4j;

/**
 * Configuration for the SLM related services
 */
@Configuration
@Slf4j
public class ModelConfig {

    /**
     * Configure model usage based on application properties
     */
    @Bean
    @Primary
    @ConditionalOnProperty(name = "recommendation.model.enabled", havingValue = "true", matchIfMissing = true)
    public ModelService modelService(RecommendationProperties properties, ModelProvider modelProvider) {
        log.info("Configuring model service with model: {}", properties.getModel().getName());
        return new ModelService(properties, modelProvider);
    }

    /**
     * Provide a default model provider when none is explicitly configured
     */
    @Bean
    @ConditionalOnMissingBean(ModelProvider.class)
    public ModelProvider defaultModelProvider(RecommendationProperties properties) {
        log.info("Creating default mock model provider");
        return new MockModelProvider(properties);
    }

    /**
     * Configure the embedding cache
     */
    @Bean
    @ConditionalOnProperty(name = "recommendation.cache.enabled", havingValue = "true", matchIfMissing = true)
    public EmbeddingCacheService embeddingCacheService(RecommendationProperties properties) {
        log.info("Configuring embedding cache with TTL: {} minutes, max size: {}",
                properties.getCache().getTtlMinutes(), properties.getCache().getMaxSize());
        return new EmbeddingCacheService(properties);
    }
}
