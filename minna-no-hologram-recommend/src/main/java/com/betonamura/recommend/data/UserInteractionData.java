package com.betonamura.recommend.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.betonamura.recommend.domain.user.UserInteraction;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * In-memory user interaction data provider.
 * This replaces the database-backed repository for user interactions.
 */
@Component
@Slf4j
public class UserInteractionData {

    private final Map<String, List<UserInteraction>> userInteractions = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    /**
     * Initialize with dummy data
     */
    @PostConstruct
    public void init() {
        initializeUserInteractions();
    }

    /**
     * Find all interactions for a user
     */
    public List<UserInteraction> findByUserId(String userId) {
        return userInteractions.getOrDefault(userId, new ArrayList<>());
    }

    /**
     * Find interactions for a user with a specific action
     */
    public List<UserInteraction> findByUserIdAndAction(String userId, String action) {
        return userInteractions.getOrDefault(userId, new ArrayList<>())
                .stream()
                .filter(ui -> ui.getAction().equals(action))
                .collect(Collectors.toList());
    }

    /**
     * Find recent user interactions, ordered by timestamp
     */
    public List<UserInteraction> findRecentByUserId(String userId) {
        return userInteractions.getOrDefault(userId, new ArrayList<>())
                .stream()
                .sorted(Comparator.comparing(UserInteraction::getInteractionTime).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Find most watched videos for a user
     */
    public List<String> findMostWatchedVideoIds(String userId) {
        Map<String, Long> countMap = userInteractions.getOrDefault(userId, new ArrayList<>())
                .stream()
                .filter(ui -> "WATCH".equals(ui.getAction()))
                .collect(Collectors.groupingBy(UserInteraction::getVideoId, Collectors.counting()));

        return countMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Find user interactions since a specific date
     */
    public List<UserInteraction> findByUserIdSince(String userId, LocalDateTime since) {
        return userInteractions.getOrDefault(userId, new ArrayList<>())
                .stream()
                .filter(ui -> !ui.getInteractionTime().isBefore(since))
                .collect(Collectors.toList());
    }

    /**
     * Find trending videos based on watch count
     */
    public List<String> findTrendingVideoIds(int limit) {
        Map<String, Long> videoWatchCounts = new HashMap<>();

        for (List<UserInteraction> interactions : userInteractions.values()) {
            interactions.stream()
                    .filter(ui -> "WATCH".equals(ui.getAction()))
                    .forEach(ui -> {
                        videoWatchCounts.merge(ui.getVideoId(), 1L, Long::sum);
                    });
        }

        return videoWatchCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Save a user interaction
     */
    public UserInteraction save(UserInteraction userInteraction) {
        if (userInteraction.getId() == null) {
            userInteraction.setId(idCounter.getAndIncrement());
        }

        String userId = userInteraction.getUserId();
        userInteractions.computeIfAbsent(userId, k -> new ArrayList<>()).add(userInteraction);

        log.debug("Saved user interaction: {}", userInteraction);
        return userInteraction;
    }

    /**
     * Initialize with sample user interactions
     */
    private void initializeUserInteractions() {
        // TODO: Replace with real data when integrating with a database
        Random random = new Random();

        // For each user in our dummy data from DataProvider
        for (int i = 1; i <= 10; i++) {
            String userId = "user-" + i;
            List<UserInteraction> interactions = new ArrayList<>();

            // Generate 10-30 random interactions for each user
            int interactionCount = 10 + random.nextInt(21);
            for (int j = 0; j < interactionCount; j++) {
                // Generate random video ID (1-50)
                String videoId = "video-" + (random.nextInt(50) + 1);

                // Pick a random action
                String[] actions = { "WATCH", "LIKE", "SHARE", "SKIP", "FULL_VIEW" };
                String action = actions[random.nextInt(actions.length)];

                // Random watch time between 0 and 600 seconds (10 minutes)
                Integer watchTime = "WATCH".equals(action) || "FULL_VIEW".equals(action) ? random.nextInt(600) : null;

                // Random timestamp in the last 30 days
                LocalDateTime timestamp = LocalDateTime.now()
                        .minusDays(random.nextInt(30))
                        .minusHours(random.nextInt(24))
                        .minusMinutes(random.nextInt(60));

                UserInteraction interaction = UserInteraction.builder()
                        .id((long) (i * 100 + j))
                        .userId(userId)
                        .videoId(videoId)
                        .action(action)
                        .watchTimeSeconds(watchTime)
                        .interactionTime(timestamp)
                        .createdAt(timestamp)
                        .build();

                interactions.add(interaction);
            }

            userInteractions.put(userId, interactions);
        }

        log.info("Initialized user interactions data with {} users", userInteractions.size());
    }
}
