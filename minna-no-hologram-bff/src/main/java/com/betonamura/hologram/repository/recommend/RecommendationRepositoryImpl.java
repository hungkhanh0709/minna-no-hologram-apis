package com.betonamura.hologram.repository.recommend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.betonamura.hologram.domain.diy.DiyCard;
import com.betonamura.hologram.domain.video.VideoCard;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of RecommendationRepository that calls the recommendation
 * service API to get content recommendations.
 */
@Slf4j
@Repository
public class RecommendationRepositoryImpl implements RecommendationRepository {

    private static final String DEFAULT_USER_ID = "anonymous";
    private static final int DEFAULT_LIMIT = 5;

    @Value("${api.recommendation.host:http://localhost}")
    private String recommendationApiHost;

    @Value("${api.recommendation.port:8081}")
    private String recommendationApiPort;

    @Value("${api.recommendation.path:/recommendations}")
    private String recommendationApiPath;

    private final RestTemplate restTemplate;

    @Autowired
    public RecommendationRepositoryImpl(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Helper method to build the recommendation API URL
     * 
     * @return The fully constructed API URL
     */
    private String buildApiUrl() {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(recommendationApiHost)
                .port(recommendationApiPort)
                .path(recommendationApiPath)
                .toUriString();
    }

    /**
     * Makes a recommendation API request and returns the response
     * 
     * @param contentId   ID of the content to get recommendations for
     * @param contentType Type of content (VIDEO or DIY)
     * @param limit       Maximum number of recommendations to return
     * @return The API response or null if there was an error
     */
    private RecommendationResponse makeRecommendationRequest(String contentId, ContentType contentType, int limit) {
        try {
            String apiUrl = buildApiUrl();
            log.debug("Making recommendation request to {} for {} with ID {}", apiUrl, contentType, contentId);

            RecommendationRequest request = RecommendationRequest.builder()
                    .contentId(contentId)
                    .contentType(contentType)
                    .userId(DEFAULT_USER_ID)
                    .limit(limit > 0 ? limit : DEFAULT_LIMIT)
                    .build();

            ResponseEntity<RecommendationResponse> response = restTemplate.postForEntity(
                    apiUrl, request, RecommendationResponse.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            }

            log.warn("Unsuccessful response from recommendation API: {} for content type: {} and ID: {}",
                    response.getStatusCode(), contentType, contentId);
            return null;
        } catch (RestClientException ex) {
            log.warn("Error making recommendation request: {}", ex.getMessage());
            return null;
        }
    }

    @Override
    public List<VideoCard> getRecommendedVideos(String videoId, int limit) {
        RecommendationResponse response = makeRecommendationRequest(videoId, ContentType.VIDEO, limit);
        if (response != null && response.getVideos() != null) {
            return response.getVideos();
        }
        log.debug("No video recommendations returned for videoId: {}", videoId);
        return new ArrayList<>();
    }

    @Override
    public List<DiyCard> getRecommendedDiys(String videoId, int limit) {
        // This method gets DIY recommendations for a VIDEO, so use ContentType.VIDEO
        RecommendationResponse response = makeRecommendationRequest(videoId, ContentType.VIDEO, limit);
        if (response != null && response.getDiys() != null) {
            return response.getDiys();
        }
        log.debug("No DIY recommendations returned for videoId: {}", videoId);
        return Collections.emptyList();
    }

    @Override
    public List<VideoCard> getRecommendedVideosByDiy(String diyId, int limit) {
        // This method gets VIDEO recommendations for a DIY, so use ContentType.DIY
        RecommendationResponse response = makeRecommendationRequest(diyId, ContentType.DIY, limit);
        if (response != null && response.getVideos() != null) {
            return response.getVideos();
        }
        log.debug("No video recommendations returned for DIY id: {}", diyId);
        return Collections.emptyList();
    }

    @Override
    public List<DiyCard> getRecommendedDiysByDiy(String diyId, int limit) {
        RecommendationResponse response = makeRecommendationRequest(diyId, ContentType.DIY, limit);
        if (response != null && response.getDiys() != null) {
            return response.getDiys();
        }
        log.debug("No DIY recommendations returned for DIY id: {}", diyId);
        return Collections.emptyList();
    }
}
