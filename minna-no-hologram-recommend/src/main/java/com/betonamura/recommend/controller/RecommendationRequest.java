package com.betonamura.recommend.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request model for the recommendations API endpoint.
 * The endpoint returns related content (videos or DIYs) based on the current
 * item's ID
 * and category. For video category, it returns related videos. For DIY
 * category, it
 * returns related DIY content.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationRequest {

    private static final int DEFAULT_LIMIT = 10;
    private static final int MIN_LIMIT = 1;
    private static final int MAX_LIMIT = 50;

    private static final String LIMIT_MIN_MSG = "Limit must be at least " + MIN_LIMIT;
    private static final String LIMIT_MAX_MSG = "Limit cannot exceed " + MAX_LIMIT;

    private String userId;
    private String categoryId;
    private String currentId;

    @Min(value = MIN_LIMIT, message = LIMIT_MIN_MSG)
    @Max(value = MAX_LIMIT, message = LIMIT_MAX_MSG)
    private Integer limit = DEFAULT_LIMIT;
}
