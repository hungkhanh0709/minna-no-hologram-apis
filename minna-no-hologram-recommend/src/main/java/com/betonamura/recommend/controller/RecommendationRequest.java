package com.betonamura.recommend.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Category ID is required (must be 'video' or 'diy')")
    private String categoryId;

    @NotBlank(message = "Current ID is required")
    private String currentId;

    @Min(value = 1, message = "Limit must be at least 1")
    @Max(value = 50, message = "Limit cannot exceed 50")
    private Integer limit = 10;
}
