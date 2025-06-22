package com.betonamura.recommend.controller;

import java.util.List;

import com.betonamura.recommend.domain.common.ContentCard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response model for the recommendations API endpoint.
 * Returns a list of related content (either videos or DIYs).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponse {
    private List<? extends ContentCard> content;
}
