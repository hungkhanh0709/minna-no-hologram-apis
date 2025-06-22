package com.betonamura.recommend.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.betonamura.recommend.controller.RecommendationRequest;
import com.betonamura.recommend.controller.RecommendationResponse;
import com.betonamura.recommend.data.DataProvider;
import com.betonamura.recommend.data.UserInteractionData;
import com.betonamura.recommend.domain.diy.DIYMetadata;
import com.betonamura.recommend.domain.diy.DiyCard;
import com.betonamura.recommend.domain.tag.Tag;
import com.betonamura.recommend.domain.video.VideoCard;
import com.betonamura.recommend.domain.video.VideoMetadata;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the recommendation service using in-memory data
 */
@Slf4j
@Repository
public class RecommendationRepositoryImpl {

    private static final String CATEGORY_VIDEO = "video";
    private static final String CATEGORY_DIY = "diy";
    private static final String CONTEXT_CURRENT_VIDEO = "currentVideo";

    private final DataProvider dataProvider;
    private final UserInteractionData userInteractionData;
    private final SlmRepository slmRepository;

    @Autowired
    public RecommendationRepositoryImpl(final DataProvider dataProvider,
            final UserInteractionData userInteractionData, final SlmRepository slmRepository) {
        this.dataProvider = dataProvider;
        this.userInteractionData = userInteractionData;
        this.slmRepository = slmRepository;
    }

    /**
     * Get related content based on the current item's category and ID.
     * For 'video' category, returns related videos.
     * For 'diy' category, returns related DIYs.
     */
    public RecommendationResponse getRecommendations(final RecommendationRequest request) {
        log.info("Generating recommendations for category: {}, current ID: {}",
                request.getCategoryId(), request.getCurrentId());

        if (CATEGORY_VIDEO.equalsIgnoreCase(request.getCategoryId())) {
            return getVideoRecommendations(request, request.getLimit());
        } else if (CATEGORY_DIY.equalsIgnoreCase(request.getCategoryId())) {
            return getDiyRecommendations(request, request.getLimit());
        } else {
            throw new IllegalArgumentException(
                    "Invalid category ID. Must be '" + CATEGORY_VIDEO + "' or '" + CATEGORY_DIY + "'");
        }
    }

    /**
     * Get video recommendations based on the current video ID.
     * If the current video exists, returns related videos using SLM service.
     * If not found, returns popular videos.
     *
     * @param request RecommendationRequest containing user, category, and current
     *                video ID
     * @param limit   Maximum number of recommendations to return
     * @return RecommendationResponse containing a list of recommended VideoCard
     *         objects
     */
    private RecommendationResponse getVideoRecommendations(final RecommendationRequest request, final int limit) {
        final Optional<VideoMetadata> currentVideoOpt = dataProvider.getVideoById(request.getCurrentId());

        if (currentVideoOpt.isPresent()) {
            final VideoMetadata currentVideo = currentVideoOpt.get();

            // Prepare context for the SLM
            final Map<String, Object> context = new HashMap<>();
            context.put(CONTEXT_CURRENT_VIDEO, currentVideo);

            // Get recommended video IDs from SLM service
            final Set<String> excludeVideoIds = new HashSet<>(Arrays.asList(request.getCurrentId()));
            final List<String> recommendedVideoIds = slmRepository.getRecommendations(
                    context, excludeVideoIds, request.getLimit());

            // Convert video IDs to VideoCard objects
            final List<VideoCard> relatedContent = new ArrayList<>();
            for (final String videoId : recommendedVideoIds) {
                final Optional<VideoMetadata> videoOpt = dataProvider.getVideoById(videoId);
                if (videoOpt.isPresent()) {
                    final VideoMetadata video = videoOpt.get();
                    final VideoCard videoCard = convertToVideoCard(video,
                            calculateSimilarityScore(currentVideo, video));
                    relatedContent.add(videoCard);
                }
            }

            return RecommendationResponse.builder()
                    .content(relatedContent)
                    .build();
        } else {
            // Current video not found - return popular videos
            final List<String> popularVideos = userInteractionData.findTrendingVideoIds(request.getLimit());
            final List<VideoCard> relatedContent = popularVideos.stream()
                    .map(videoId -> dataProvider.getVideoById(videoId))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(video -> convertToVideoCard(video, 0.5))
                    .limit(limit)
                    .collect(Collectors.toList());

            return RecommendationResponse.builder()
                    .content(relatedContent)
                    .build();
        }
    }

    /**
     * Get DIY recommendations based on the current DIY ID.
     * If the current DIY exists, returns related DIYs in the same category
     * (excluding the current one).
     * If not found, returns a random selection of DIYs.
     *
     * @param request RecommendationRequest containing user, category, and current
     *                DIY ID
     * @param limit   Maximum number of recommendations to return
     * @return RecommendationResponse containing a list of recommended DiyCard
     *         objects
     */
    private RecommendationResponse getDiyRecommendations(final RecommendationRequest request, final int limit) {

        // Get the current DIY metadata
        final Optional<DIYMetadata> currentDiyOpt = dataProvider.getDIYById(request.getCurrentId());

        if (currentDiyOpt.isPresent()) {
            final DIYMetadata currentDiy = currentDiyOpt.get();

            // Get related DIYs in the same category
            final List<DIYMetadata> relatedDIYs = dataProvider.getDIYsByCategory(currentDiy.getCategoryId());
            final List<DiyCard> relatedContent = relatedDIYs.stream()
                    .filter(diy -> !diy.getDiyId().equals(request.getCurrentId()))
                    .map(diy -> convertToDiyCard(diy, 0.8))
                    .limit(limit)
                    .collect(Collectors.toList());

            return RecommendationResponse.builder()
                    .content(relatedContent)
                    .build();
        } else {
            // Current DIY not found - return random DIYs
            final List<DIYMetadata> popularDIYs = new ArrayList<>(dataProvider.getAllDIYs());
            Collections.shuffle(popularDIYs);
            final List<DiyCard> relatedContent = popularDIYs.stream()
                    .map(diy -> convertToDiyCard(diy, 0.5))
                    .limit(limit)
                    .collect(Collectors.toList());

            return RecommendationResponse.builder()
                    .content(relatedContent)
                    .build();
        }
    }

    /**
     * Calculate similarity score between two videos
     */
    private double calculateSimilarityScore(final VideoMetadata video1, final VideoMetadata video2) {
        // Basic similarity calculation based on category and tags
        if (video1.getCategoryId().equals(video2.getCategoryId())) {
            // Same category - high base similarity
            double similarity = 0.7;

            // Check tags for additional similarity
            if (video1.getTags() != null && video2.getTags() != null) {
                final Set<String> tags1 = new HashSet<>(Arrays.asList(video1.getTags().split(",")));
                final Set<String> tags2 = new HashSet<>(Arrays.asList(video2.getTags().split(",")));

                // Count matching tags
                final Set<String> intersection = new HashSet<>(tags1);
                intersection.retainAll(tags2);

                // Boost similarity based on tag overlap
                similarity += 0.1 * intersection.size();
            }

            return Math.min(0.95, similarity); // Cap at 0.95
        } else {
            // Different category - lower base similarity
            return 0.3;
        }
    }

    /**
     * Convert VideoMetadata to VideoCard format
     */
    private VideoCard convertToVideoCard(final VideoMetadata video, final double similarityScore) {
        final List<Tag> tags = new ArrayList<>();
        if (video.getTags() != null && !video.getTags().isEmpty()) {
            final String[] tagStrings = video.getTags().split(",");
            for (final String tagStr : tagStrings) {
                tags.add(Tag.builder()
                        .id(tagStr.trim().toLowerCase())
                        .name(tagStr.trim())
                        .build());
            }
        }

        return VideoCard.builder()
                .id(video.getVideoId())
                .slug("video-" + video.getVideoId()) // Generate slug from ID
                .title(video.getTitle())
                .thumbnail(video.getThumbnailUrl())
                .category(video.getCategoryId())
                .tags(tags)
                .likeCount(video.getLikeCount() != null ? video.getLikeCount() : 0)
                .similarityScore(similarityScore)
                .build();
    }

    /**
     * Convert DIYMetadata to DiyCard format
     */
    private DiyCard convertToDiyCard(final DIYMetadata diy, final double similarityScore) {
        final List<Tag> tags = new ArrayList<>();
        if (diy.getTags() != null && !diy.getTags().isEmpty()) {
            final String[] tagStrings = diy.getTags().split(",");
            for (final String tagStr : tagStrings) {
                tags.add(Tag.builder()
                        .id(tagStr.trim().toLowerCase())
                        .name(tagStr.trim())
                        .build());
            }
        }

        final String difficulty;
        if (diy.getDifficulty() == null) {
            difficulty = "Medium";
        } else {
            switch (diy.getDifficulty()) {
                case 1 -> difficulty = "Easy";
                case 2 -> difficulty = "Medium";
                default -> difficulty = "Hard";
            }
        }
        return DiyCard.builder()
                .id(diy.getDiyId())
                .slug("diy-" + diy.getDiyId()) // Generate slug from ID
                .title(diy.getTitle())
                .thumbnail(diy.getThumbnailUrl())
                .summary(diy.getDescription())
                .stepCount(5) // Hardcoded for now
                .estimatedTime("30min") // Hardcoded for now
                .difficulty(difficulty)
                .tags(tags)
                .likeCount(diy.getLikeCount() != null ? diy.getLikeCount() : 0)
                .similarityScore(similarityScore)
                .build();
    }
}
