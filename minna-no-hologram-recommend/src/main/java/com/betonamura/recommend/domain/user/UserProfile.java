package com.betonamura.recommend.domain.user;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Domain model for the user profile.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private Long id;
    private String userId;
    private String preferredCategories;
    private String preferredLanguage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private byte[] embedding;
}
