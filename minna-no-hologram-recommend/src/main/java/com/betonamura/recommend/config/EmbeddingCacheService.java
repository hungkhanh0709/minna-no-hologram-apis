package com.betonamura.recommend.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.betonamura.recommend.domain.embedding.ContentEmbedding;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

/**
 * Service to cache content embeddings with TTL expiration
 */
@Slf4j
@Service
public class EmbeddingCacheService {
    private final RecommendationProperties properties;
    private final Map<String, ContentEmbedding> cache = new ConcurrentHashMap<>();
    private final ScheduledExecutorService cleanupScheduler = Executors.newScheduledThreadPool(1);

    public EmbeddingCacheService(RecommendationProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void init() {
        // Schedule periodic cleanup of expired cache entries
        cleanupScheduler.scheduleAtFixedRate(
                this::cleanupExpiredEntries,
                properties.getCache().getTtlMinutes(),
                properties.getCache().getTtlMinutes(),
                TimeUnit.MINUTES);
        log.info("Embedding cache initialized with TTL: {} minutes", properties.getCache().getTtlMinutes());
    }

    @PreDestroy
    public void shutdown() {
        cleanupScheduler.shutdown();
        log.info("Embedding cache scheduler shutdown");
    }

    /**
     * Get a cached embedding or null if not in cache
     */
    public ContentEmbedding get(String key) {
        ContentEmbedding embedding = cache.get(key);
        if (embedding != null) {
            // Check if expired
            long expirationTimeMs = embedding.getTimestamp()
                    + TimeUnit.MINUTES.toMillis(properties.getCache().getTtlMinutes());
            if (System.currentTimeMillis() > expirationTimeMs) {
                cache.remove(key);
                return null;
            }
        }
        return embedding;
    }

    /**
     * Store an embedding in the cache
     */
    public void put(String key, ContentEmbedding embedding) {
        // Enforce max size
        if (cache.size() >= properties.getCache().getMaxSize() && !cache.containsKey(key)) {
            // Cache is full, remove oldest entry
            String oldestKey = findOldestEntry();
            if (oldestKey != null) {
                cache.remove(oldestKey);
                log.debug("Removed oldest cache entry: {}", oldestKey);
            }
        }

        cache.put(key, embedding);
    }

    /**
     * Remove expired entries from cache
     */
    private void cleanupExpiredEntries() {
        long now = System.currentTimeMillis();
        long expirationMs = TimeUnit.MINUTES.toMillis(properties.getCache().getTtlMinutes());

        int removedCount = 0;
        for (Map.Entry<String, ContentEmbedding> entry : cache.entrySet()) {
            if (now - entry.getValue().getTimestamp() > expirationMs) {
                cache.remove(entry.getKey());
                removedCount++;
            }
        }

        if (removedCount > 0) {
            log.debug("Removed {} expired entries from embedding cache", removedCount);
        }
    }

    /**
     * Find the oldest entry in the cache
     */
    private String findOldestEntry() {
        String oldestKey = null;
        long oldestTimestamp = Long.MAX_VALUE;

        for (Map.Entry<String, ContentEmbedding> entry : cache.entrySet()) {
            if (entry.getValue().getTimestamp() < oldestTimestamp) {
                oldestTimestamp = entry.getValue().getTimestamp();
                oldestKey = entry.getKey();
            }
        }

        return oldestKey;
    }

    /**
     * Clear the cache
     */
    public void clearCache() {
        cache.clear();
        log.info("Embedding cache cleared");
    }

    /**
     * Get the number of entries in the cache
     */
    public int size() {
        return cache.size();
    }
}
