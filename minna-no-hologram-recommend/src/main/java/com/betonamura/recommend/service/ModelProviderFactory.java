package com.betonamura.recommend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.betonamura.recommend.config.RecommendationProperties;

import lombok.extern.slf4j.Slf4j;

/**
 * Factory that creates appropriate model provider based on configuration
 */
@Configuration
@Slf4j
public class ModelProviderFactory {

    private final RecommendationProperties properties;

    @Autowired
    public ModelProviderFactory(RecommendationProperties properties) {
        this.properties = properties;
    }

    /**
     * Create a model provider based on configuration
     */
    @Bean
    @org.springframework.context.annotation.Primary
    public ModelProvider createModelProvider() {
        String provider = properties.getModel().getProvider();
        log.info("Creating model provider for: {}", provider);

        switch (provider.toLowerCase()) {
            case "huggingface":
                return createHuggingFaceProvider();
            case "openai":
                return createOpenAIProvider();
            case "tensorflow":
                return createTensorFlowProvider();
            case "mock":
            default:
                return createMockProvider();
        }
    }

    private ModelProvider createHuggingFaceProvider() {
        try {
            log.info("Creating HuggingFace model provider with model: {}", properties.getModel().getName());

            // TODO: Create and configure actual provider when implementations are ready
            return new MockModelProvider(properties);
        } catch (Exception e) {
            log.error("Failed to create HuggingFace provider", e);
            // Fallback to mock provider
            return createMockProvider();
        }
    }

    private ModelProvider createOpenAIProvider() {
        try {
            String apiKey = properties.getModel().getApiKey();
            if (apiKey == null || apiKey.trim().isEmpty()) {
                log.error("OpenAI API key not configured");
                return createMockProvider();
            }

            log.info("Creating OpenAI model provider");
            // TODO: Create and configure actual provider when implementations are ready
            return new MockModelProvider(properties);
        } catch (Exception e) {
            log.error("Failed to create OpenAI provider", e);
            // Fallback to mock provider
            return createMockProvider();
        }
    }

    private ModelProvider createTensorFlowProvider() {
        try {
            log.info("Creating TensorFlow model provider");
            // TODO: Create and configure actual provider when implementations are ready
            return new MockModelProvider(properties);
        } catch (Exception e) {
            log.error("Failed to create TensorFlow provider", e);
            // Fallback to mock provider
            return createMockProvider();
        }
    }

    private ModelProvider createMockProvider() {
        log.info("Creating mock model provider");
        return new MockModelProvider(properties);
    }
}
