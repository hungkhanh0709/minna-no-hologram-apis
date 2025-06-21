package com.betonamura.recommend.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.betonamura.recommend.domain.user.UserHistory;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * In-memory user history data provider.
 * This replaces the database-backed repository for user watch history.
 */
@Component
@Slf4j
public class UserHistoryData {

    private final Map<String, List<UserHistory>> userHistories = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    /**
     * Initialize with dummy data
     */
    @PostConstruct
    public void init() {
        initializeUserHistory();
    }

    /**
     * Find all watch history for a user
     */
    public List<UserHistory> findByUserId(String userId) {
        return userHistories.getOrDefault(userId, new ArrayList<>());
    }

    /**
     * Find most watched videos for a user
     */
    public List<String> findMostWatchedVideoIds(String userId, int limit) {
        Map<String, Long> countMap = userHistories.getOrDefault(userId, new ArrayList<>())
                .stream()
                .collect(Collectors.groupingBy(UserHistory::getVideoId, Collectors.counting()));

        return countMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Get videos that a user has completed watching
     */
    public List<UserHistory> findCompletedVideos(String userId) {
        return userHistories.getOrDefault(userId, new ArrayList<>())
                .stream()
                .filter(UserHistory::getCompleted)
                .collect(Collectors.toList());
    }

    /**
     * Find user history since a specific date
     */
    public List<UserHistory> findByUserIdSince(String userId, LocalDateTime since) {
        return userHistories.getOrDefault(userId, new ArrayList<>())
                .stream()
                .filter(uh -> !uh.getWatchedAt().isBefore(since))
                .collect(Collectors.toList());
    }

    /**
     * Save a user history entry
     */
    public UserHistory save(UserHistory userHistory) {
        if (userHistory.getId() == null) {
            userHistory.setId(idCounter.getAndIncrement());
        }

        String userId = userHistory.getUserId();
        userHistories.computeIfAbsent(userId, k -> new ArrayList<>()).add(userHistory);

        log.debug("Saved user history: {}", userHistory);
        return userHistory;
    }

    /**
     * Initialize with sample user history data
     */
    private void initializeUserHistory() {
        // TODO: Replace with real data when integrating with a database
        Random random = new Random();

        // For each user
        for (int i = 1; i <= 10; i++) {
            String userId = "user-" + i;
            List<UserHistory> historyList = new ArrayList<>();

            // Generate 5-15 watch history entries
            int historyCount = 5 + random.nextInt(11);
            for (int j = 0; j < historyCount; j++) {
                // Generate random video ID (1-50)
                String videoId = "video-" + (random.nextInt(50) + 1);

                // Random watch duration between 10 and 300 seconds
                int watchDuration = 10 + random.nextInt(291);

                // 70% chance the video was watched to completion
                boolean completed = random.nextDouble() < 0.7;

                // Random timestamp in the last 30 days
                LocalDateTime timestamp = LocalDateTime.now()
                        .minusDays(random.nextInt(30))
                        .minusHours(random.nextInt(24))
                        .minusMinutes(random.nextInt(60));

                UserHistory history = UserHistory.builder()
                        .id((long) (i * 100 + j))
                        .userId(userId)
                        .videoId(videoId)
                        .watchDurationSeconds(watchDuration)
                        .completed(completed)
                        .watchedAt(timestamp)
                        .build();

                historyList.add(history);
            }

            userHistories.put(userId, historyList);
        }

        log.info("Initialized user history data with {} users", userHistories.size());
    }
}
