package com.betonamura.recommend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.betonamura.recommend.repository.SlmRepository;
import com.betonamura.recommend.service.ContentEmbeddingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Configuration for scheduling model updates and embedding regeneration
 */
@Configuration
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class SchedulingConfig {

    private final SlmRepository slmRepository;
    private final ContentEmbeddingService embeddingService;

    /**
     * Refresh model embeddings daily at 2 AM
     * This keeps recommendations fresh as new content is added
     */
    @Scheduled(cron = "0 0 2 * * ?") // Run at 2 AM every day
    public void refreshEmbeddings() {
        log.info("Scheduled task: refreshing content embeddings");
        embeddingService.precomputeAllEmbeddings();
    }

    /**
     * Refresh the model based on new data weekly at 3 AM on Sunday
     * In a real system, this would retrain the model with new user data
     */
    @Scheduled(cron = "0 0 3 * * SUN") // Run at 3 AM every Sunday
    public void retrainModel() {
        log.info("Scheduled task: retraining recommendation model");
        slmRepository.trainModel();
    }
}
