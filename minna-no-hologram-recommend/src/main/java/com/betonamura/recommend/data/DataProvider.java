package com.betonamura.recommend.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.betonamura.recommend.domain.common.ContentType;
import com.betonamura.recommend.domain.diy.DIYMetadata;
import com.betonamura.recommend.domain.video.VideoMetadata;

import jakarta.annotation.PostConstruct;

/**
 * In-memory data provider service to replace database dependency.
 * Provides sample data for recommendations.
 */
@Component
public class DataProvider {

    private final List<VideoMetadata> videos = new ArrayList<>();
    private final List<DIYMetadata> diys = new ArrayList<>();
    private final Map<String, List<String>> userWatchHistory = new HashMap<>();

    /**
     * Initialize with dummy data
     */
    @PostConstruct
    public void init() {
        initializeVideos();
        initializeDIYs();
        initializeUserWatchHistory();
    }

    /**
     * Get all video metadata
     */
    public List<VideoMetadata> getAllVideos() {
        return Collections.unmodifiableList(videos);
    }

    /**
     * Get video by ID
     */
    public Optional<VideoMetadata> getVideoById(String videoId) {
        return videos.stream()
                .filter(v -> v.getVideoId().equals(videoId))
                .findFirst();
    }

    /**
     * Get videos by category
     */
    public List<VideoMetadata> getVideosByCategory(String categoryId) {
        return videos.stream()
                .filter(v -> v.getCategoryId().equals(categoryId))
                .collect(Collectors.toList());
    }

    /**
     * Get all DIY metadata
     */
    public List<DIYMetadata> getAllDIYs() {
        return Collections.unmodifiableList(diys);
    }

    /**
     * Get DIY by ID
     */
    public Optional<DIYMetadata> getDIYById(String diyId) {
        return diys.stream()
                .filter(d -> d.getDiyId().equals(diyId))
                .findFirst();
    }

    /**
     * Get DIYs by category
     */
    public List<DIYMetadata> getDIYsByCategory(String categoryId) {
        return diys.stream()
                .filter(d -> d.getCategoryId().equals(categoryId))
                .collect(Collectors.toList());
    }

    /**
     * Get user watch history
     */
    public List<String> getUserWatchHistory(String userId) {
        return userWatchHistory.getOrDefault(userId, Collections.emptyList());
    }

    /**
     * Record new watch entry
     */
    public void recordWatchHistory(String userId, String videoId) {
        userWatchHistory.computeIfAbsent(userId, k -> new ArrayList<>()).add(videoId);
    }

    /**
     * Get popular videos based on view count
     */
    public List<VideoMetadata> getPopularVideos(int limit) {
        return videos.stream()
                .sorted(Comparator.comparing(VideoMetadata::getViewCount).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * Get popular DIYs based on view count
     */
    public List<DIYMetadata> getPopularDIYs(int limit) {
        return diys.stream()
                .sorted(Comparator.comparing(DIYMetadata::getViewCount).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    /*
     * Initialize with sample videos
     */
    private void initializeVideos() {
        // TODO: Replace with real data when integrating with a database
        String[] categories = { "music", "gaming", "education", "tech", "entertainment" };
        String[] titles = {
                "Amazing Holographic Concert",
                "Hologram Gaming Experience",
                "Educational 3D Projection",
                "Latest Tech Hologram Review",
                "Entertainment Holographic Show"
        };

        for (int i = 0; i < 50; i++) {
            int categoryIndex = i % categories.length;
            videos.add(
                    VideoMetadata.builder()
                            .videoId("video-" + (i + 1))
                            .title(titles[categoryIndex] + " #" + (i + 1))
                            .description("This is a sample holographic video #" + (i + 1) + " in the "
                                    + categories[categoryIndex] + " category.")
                            .thumbnailUrl("https://example.com/thumbnail" + (i + 1) + ".jpg")
                            .categoryId(categories[categoryIndex])
                            .tags(categories[categoryIndex] + ",hologram,3d,projection")
                            .contentType(ContentType.VIDEO)
                            .viewCount(new Random().nextInt(10000))
                            .likeCount(new Random().nextInt(1000))
                            .build());
        }
    }

    /*
     * Initialize with sample DIYs
     */
    private void initializeDIYs() {
        // TODO: Replace with real data when integrating with a database
        String[] categories = { "beginner", "intermediate", "advanced", "kids", "professional" };
        String[] titles = {
                "Simple Smartphone Hologram",
                "Interactive Holographic Display",
                "Professional Hologram Projector",
                "Kid-friendly Hologram Toy",
                "Studio-quality Holographic System"
        };

        for (int i = 0; i < 30; i++) {
            int categoryIndex = i % categories.length;
            diys.add(
                    DIYMetadata.builder()
                            .diyId("diy-" + (i + 1))
                            .title(titles[categoryIndex] + " #" + (i + 1))
                            .description(
                                    "Learn how to create your own " + titles[categoryIndex] + " with common materials.")
                            .thumbnailUrl("https://example.com/diy-thumbnail" + (i + 1) + ".jpg")
                            .categoryId(categories[categoryIndex])
                            .tags(categories[categoryIndex] + ",diy,hologram,maker")
                            .contentType(ContentType.DIY)
                            .difficulty(categoryIndex + 1)
                            .viewCount(new Random().nextInt(5000))
                            .likeCount(new Random().nextInt(500))
                            .build());
        }
    }

    /*
     * Initialize with sample user watch history
     */
    private void initializeUserWatchHistory() {
        // TODO: Replace with real data when integrating with a database
        for (int i = 1; i <= 10; i++) {
            String userId = "user-" + i;
            List<String> watchHistory = new ArrayList<>();

            // Each user watches 5-15 videos randomly
            int watchCount = 5 + new Random().nextInt(11);
            for (int j = 0; j < watchCount; j++) {
                int videoIndex = new Random().nextInt(videos.size());
                watchHistory.add(videos.get(videoIndex).getVideoId());
            }

            userWatchHistory.put(userId, watchHistory);
        }
    }
}
