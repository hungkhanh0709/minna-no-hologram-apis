package com.betonamura.recommend.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.betonamura.recommend.config.EmbeddingCacheService;
import com.betonamura.recommend.config.RecommendationProperties;
import com.betonamura.recommend.data.DataProvider;
import com.betonamura.recommend.domain.diy.DIYMetadata;
import com.betonamura.recommend.domain.embedding.ContentEmbedding;
import com.betonamura.recommend.domain.video.VideoMetadata;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service for precomputing and managing content embeddings
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ContentEmbeddingService {

    private final ModelService modelService;
    private final DataProvider dataProvider;
    private final RecommendationProperties properties;
    private final EmbeddingCacheService embeddingCache;

    @PostConstruct
    public void init() {
        if (properties.getPrecomputeEmbeddings()) {
            // Don't block startup - precompute embeddings asynchronously
            CompletableFuture.runAsync(this::precomputeAllEmbeddings)
                    .exceptionally(ex -> {
                        log.error("Error precomputing embeddings", ex);
                        return null;
                    });
        }
    }

    /**
     * Precompute embeddings for all content in the system
     */
    @Async
    public void precomputeAllEmbeddings() {
        try {
            log.info("Starting precomputation of content embeddings");

            // Process videos
            List<VideoMetadata> videos = dataProvider.getAllVideos();
            log.info("Precomputing embeddings for {} videos", videos.size());

            for (VideoMetadata video : videos) {
                try {
                    String contentDescription = modelService.generateContentDescription(video);
                    float[] embedding = modelService.generateEmbedding(contentDescription);

                    ContentEmbedding contentEmbedding = ContentEmbedding.builder()
                            .contentId(video.getVideoId())
                            .contentType("video")
                            .vector(embedding)
                            .timestamp(System.currentTimeMillis())
                            .build();

                    embeddingCache.put("video:" + video.getVideoId(), contentEmbedding);
                } catch (Exception e) {
                    log.error("Error generating embedding for video {}", video.getVideoId(), e);
                }
            }

            // Process DIYs
            List<DIYMetadata> diys = dataProvider.getAllDIYs();
            log.info("Precomputing embeddings for {} DIYs", diys.size());

            for (DIYMetadata diy : diys) {
                try {
                    // TODO: Implement DIY description generation
                    StringBuilder contentDescription = new StringBuilder();
                    contentDescription.append("Title: ").append(diy.getTitle()).append(". ");
                    if (diy.getDescription() != null) {
                        contentDescription.append("Description: ").append(diy.getDescription()).append(". ");
                    }
                    if (diy.getTags() != null) {
                        contentDescription.append("Tags: ").append(diy.getTags().replace(",", ", ")).append(". ");
                    }

                    float[] embedding = modelService.generateEmbedding(contentDescription.toString());

                    ContentEmbedding contentEmbedding = ContentEmbedding.builder()
                            .contentId(diy.getDiyId())
                            .contentType("diy")
                            .vector(embedding)
                            .timestamp(System.currentTimeMillis())
                            .build();

                    embeddingCache.put("diy:" + diy.getDiyId(), contentEmbedding);
                } catch (Exception e) {
                    log.error("Error generating embedding for DIY {}", diy.getDiyId(), e);
                }
            }

            log.info("Completed precomputation of content embeddings. Cache size: {}", embeddingCache.size());
        } catch (Exception e) {
            log.error("Error during embedding precomputation", e);
        }
    }

    /**
     * Get or create an embedding for a video
     */
    public ContentEmbedding getVideoEmbedding(VideoMetadata video) {
        String key = "video:" + video.getVideoId();
        ContentEmbedding embedding = embeddingCache.get(key);

        if (embedding == null) {
            try {
                String contentDescription = modelService.generateContentDescription(video);
                float[] vector = modelService.generateEmbedding(contentDescription);

                embedding = ContentEmbedding.builder()
                        .contentId(video.getVideoId())
                        .contentType("video")
                        .vector(vector)
                        .timestamp(System.currentTimeMillis())
                        .build();

                embeddingCache.put(key, embedding);
            } catch (Exception e) {
                log.error("Error generating embedding for video {}", video.getVideoId(), e);
                throw new RuntimeException("Failed to generate embedding", e);
            }
        }

        return embedding;
    }

    /**
     * Get or create an embedding for a DIY
     */
    public ContentEmbedding getDIYEmbedding(DIYMetadata diy) {
        String key = "diy:" + diy.getDiyId();
        ContentEmbedding embedding = embeddingCache.get(key);

        if (embedding == null) {
            try {
                // TODO: Implement DIY description generation
                StringBuilder contentDescription = new StringBuilder();
                contentDescription.append("Title: ").append(diy.getTitle()).append(". ");
                if (diy.getDescription() != null) {
                    contentDescription.append("Description: ").append(diy.getDescription()).append(". ");
                }
                if (diy.getTags() != null) {
                    contentDescription.append("Tags: ").append(diy.getTags().replace(",", ", ")).append(". ");
                }

                float[] vector = modelService.generateEmbedding(contentDescription.toString());

                embedding = ContentEmbedding.builder()
                        .contentId(diy.getDiyId())
                        .contentType("diy")
                        .vector(vector)
                        .timestamp(System.currentTimeMillis())
                        .build();

                embeddingCache.put(key, embedding);
            } catch (Exception e) {
                log.error("Error generating embedding for DIY {}", diy.getDiyId(), e);
                throw new RuntimeException("Failed to generate embedding", e);
            }
        }

        return embedding;
    }
}
