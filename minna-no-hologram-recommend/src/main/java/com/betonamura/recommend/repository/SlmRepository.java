package com.betonamura.recommend.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Service for handling Small Language Model (SLM) operations
 */
public interface SlmRepository {
    /**
     * Get video recommendations using the SLM
     *
     * @param context         Map containing context data (current video, user
     *                        history, etc.)
     * @param excludeVideoIds Set of video IDs to exclude from recommendations
     * @param limit           Maximum number of recommendations to return
     * @return List of recommended video IDs in order of relevance
     */
    List<String> getRecommendations(Map<String, Object> context, Set<String> excludeVideoIds, int limit);

    /**
     * Train or update the model with new data
     */
    void trainModel();

    /**
     * Initialize the model when the application starts
     */
    void initModel();
}
