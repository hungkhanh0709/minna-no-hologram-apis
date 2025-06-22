package com.betonamura.recommend.config;

import org.springframework.context.annotation.Configuration;

/**
 * API Configuration for the Recommend module.
 */
@Configuration
public class ApiConfig {

    // API endpoint paths
    public static final String API_BASE = "/v1";
    public static final String RECOMMENDATIONS = API_BASE + "/recommendations";

    // Default limits and parameters
    public static final int DEFAULT_RECOMMENDATION_LIMIT = 2;
    public static final int MAX_RECOMMENDATION_LIMIT = 10;
}
