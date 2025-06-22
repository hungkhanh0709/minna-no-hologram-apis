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
    public static final String POPULAR = RECOMMENDATIONS + "/popular";
    public static final String FEEDBACK = API_BASE + "/feedback";

    // Admin endpoints
    public static final String ADMIN = "/admin";
    public static final String MODEL_INFO = ADMIN + "/model/info";
    public static final String MODEL_REFRESH = ADMIN + "/model/refresh";

    // Default limits and parameters
    public static final int DEFAULT_RECOMMENDATION_LIMIT = 2;
    public static final int MAX_RECOMMENDATION_LIMIT = 10;
}
