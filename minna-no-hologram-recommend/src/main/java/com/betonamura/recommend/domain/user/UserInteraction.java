package com.betonamura.recommend.domain.user;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for user interaction data.
 * This is a simple in-memory POJO that replaces the JPA entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInteraction {
    private Long id;
    private String userId;
    private String videoId;
    private String action; // WATCH, LIKE, SHARE, FULL_VIEW, SKIP
    private Integer watchTimeSeconds;
    private LocalDateTime interactionTime;
    private LocalDateTime createdAt;
}
