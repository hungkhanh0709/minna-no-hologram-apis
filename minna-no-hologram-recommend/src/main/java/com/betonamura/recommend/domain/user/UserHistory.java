package com.betonamura.recommend.domain.user;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for user watch history data.
 * This is a simple in-memory POJO that replaces the JPA entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserHistory {
    private Long id;
    private String userId;
    private String videoId;
    private Integer watchDurationSeconds;
    private Boolean completed;
    private LocalDateTime watchedAt;
}
