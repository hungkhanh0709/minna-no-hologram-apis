package com.betonamura.recommend.service;

import com.betonamura.recommend.controller.RecommendationRequest;
import com.betonamura.recommend.controller.RecommendationResponse;

/**
 * Service interface for generating recommendations.
 */
public interface RecommendationService {
    /**
     * Get a list of related content based on the current item's ID and category.
     * For 'video' category, returns related videos.
     * For 'diy' category, returns related DIYs.
     *
     * @param request The recommendation request with required category ID
     *                (video/diy), current item ID,
     *                and optional limit parameter
     * @return Response containing a list of related content (either videos or DIYs)
     */
    RecommendationResponse getRecommendations(RecommendationRequest request);

}
