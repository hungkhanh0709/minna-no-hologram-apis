package com.betonamura.recommend.service.impl;

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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.betonamura.recommend.controller.RecommendationRequest;
import com.betonamura.recommend.controller.RecommendationResponse;
import com.betonamura.recommend.data.DataProvider;
import com.betonamura.recommend.data.UserInteractionData;
import com.betonamura.recommend.domain.diy.DIYMetadata;
import com.betonamura.recommend.domain.diy.DiyCard;
import com.betonamura.recommend.domain.tag.Tag;
import com.betonamura.recommend.domain.video.VideoCard;
import com.betonamura.recommend.domain.video.VideoMetadata;
import com.betonamura.recommend.service.RecommendationService;
import com.betonamura.recommend.service.SlmService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the recommendation service using in-memory data
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationServiceImpl implements RecommendationService {

    private final DataProvider dataProvider;
    private final UserInteractionData userInteractionData;
    private final SlmService slmService;

    @Value("${recommendation.default-limit:10}")
    private int defaultLimit;

    /**
     * Get related content based on the current item's category and ID.
     * For 'video' category, returns related videos.
     * For 'diy' category, returns related DIYs.
     */
    @Override
    public RecommendationResponse getRecommendations(RecommendationRequest request) {
        log.info("Generating recommendations for category: {}, current ID: {}",
                request.getCategoryId(), request.getCurrentId());

        // Set limit to default if not provided
        int limit = request.getLimit() != null ? request.getLimit() : defaultLimit;

        if ("video".equalsIgnoreCase(request.getCategoryId())) {
            // Get video recommendations
            Optional<VideoMetadata> currentVideoOpt = dataProvider.getVideoById(request.getCurrentId());

            if (currentVideoOpt.isPresent()) {
                VideoMetadata currentVideo = currentVideoOpt.get();

                // Prepare context for the SLM
                Map<String, Object> context = new HashMap<>();
                context.put("currentVideo", currentVideo);

                // Get recommended video IDs from SLM service
                Set<String> excludeVideoIds = new HashSet<>(Arrays.asList(request.getCurrentId()));
                List<String> recommendedVideoIds = slmService.getRecommendations(
                        context, excludeVideoIds, limit);

                // Convert video IDs to VideoCard objects
                List<VideoCard> relatedContent = new ArrayList<>();
                for (String videoId : recommendedVideoIds) {
                    Optional<VideoMetadata> videoOpt = dataProvider.getVideoById(videoId);
                    if (videoOpt.isPresent()) {
                        VideoMetadata video = videoOpt.get();
                        VideoCard videoCard = convertToVideoCard(video,
                                calculateSimilarityScore(currentVideo, video));
                        relatedContent.add(videoCard);
                    }
                }

                return RecommendationResponse.builder()
                        .content(relatedContent)
                        .build();
            } else {
                // Current video not found - return popular videos
                List<String> popularVideos = userInteractionData.findTrendingVideoIds(limit);
                List<VideoCard> relatedContent = popularVideos.stream()
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
        } else if ("diy".equalsIgnoreCase(request.getCategoryId())) {
            // Get DIY recommendations
            Optional<DIYMetadata> currentDiyOpt = dataProvider.getDIYById(request.getCurrentId());

            if (currentDiyOpt.isPresent()) {
                DIYMetadata currentDiy = currentDiyOpt.get();

                // Get related DIYs in the same category
                List<DIYMetadata> relatedDIYs = dataProvider.getDIYsByCategory(currentDiy.getCategoryId());
                List<DiyCard> relatedContent = relatedDIYs.stream()
                        .filter(diy -> !diy.getDiyId().equals(request.getCurrentId()))
                        .map(diy -> convertToDiyCard(diy, 0.8))
                        .limit(limit)
                        .collect(Collectors.toList());

                return RecommendationResponse.builder()
                        .content(relatedContent)
                        .build();
            } else {
                // Current DIY not found - return random DIYs
                List<DIYMetadata> popularDIYs = dataProvider.getAllDIYs();
                Collections.shuffle(popularDIYs);
                List<DiyCard> relatedContent = popularDIYs.stream()
                        .map(diy -> convertToDiyCard(diy, 0.5))
                        .limit(limit)
                        .collect(Collectors.toList());

                return RecommendationResponse.builder()
                        .content(relatedContent)
                        .build();
            }
        } else {
            throw new IllegalArgumentException("Invalid category ID. Must be 'video' or 'diy'");
        }
    }

    /**
     * Calculate similarity score between two videos
     */
    private double calculateSimilarityScore(VideoMetadata video1, VideoMetadata video2) {
        // Basic similarity calculation based on category and tags
        if (video1.getCategoryId().equals(video2.getCategoryId())) {
            // Same category - high base similarity
            double similarity = 0.7;

            // Check tags for additional similarity
            if (video1.getTags() != null && video2.getTags() != null) {
                Set<String> tags1 = new HashSet<>(Arrays.asList(video1.getTags().split(",")));
                Set<String> tags2 = new HashSet<>(Arrays.asList(video2.getTags().split(",")));

                // Count matching tags
                Set<String> intersection = new HashSet<>(tags1);
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
    private VideoCard convertToVideoCard(VideoMetadata video, double similarityScore) {
        List<Tag> tags = new ArrayList<>();
        if (video.getTags() != null && !video.getTags().isEmpty()) {
            String[] tagStrings = video.getTags().split(",");
            for (String tagStr : tagStrings) {
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
    private DiyCard convertToDiyCard(DIYMetadata diy, double similarityScore) {
        List<Tag> tags = new ArrayList<>();
        if (diy.getTags() != null && !diy.getTags().isEmpty()) {
            String[] tagStrings = diy.getTags().split(",");
            for (String tagStr : tagStrings) {
                tags.add(Tag.builder()
                        .id(tagStr.trim().toLowerCase())
                        .name(tagStr.trim())
                        .build());
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
                .difficulty(diy.getDifficulty() != null
                        ? (diy.getDifficulty() == 1 ? "Easy" : diy.getDifficulty() == 2 ? "Medium" : "Hard")
                        : "Medium")
                .tags(tags)
                .likeCount(diy.getLikeCount() != null ? diy.getLikeCount() : 0)
                .similarityScore(similarityScore)
                .build();
    }
}
