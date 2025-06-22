package com.betonamura.hologram.repository.recommend;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.betonamura.hologram.domain.ContentType;
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

    @Override
    public List<VideoCard> getRecommendedVideos(final String currentId, final int limit) {
        final RecommendationResponse response = makeRecommendationRequest(currentId, ContentType.VIDEO, limit);
        if (ObjectUtils.isEmpty(response)) {
            log.debug("No video recommendations returned for videoId: {}", currentId);
            return new ArrayList<>();
        }

        return response.getVideos();
    }

    @Override
    public List<DiyCard> getRecommendedDiys(final String currentId, final int limit) {
        RecommendationResponse response = makeRecommendationRequest(currentId, ContentType.VIDEO, limit);
        if (ObjectUtils.isEmpty(response) || ObjectUtils.isEmpty(response.getDiys())) {
            log.debug("No DIY recommendations returned for videoId: {}", currentId);
            return new ArrayList<>();
        }
        return response.getDiys();
    }

    /**
     * Makes a recommendation API request and returns the response
     * 
     * @param contentId   ID of the content to get recommendations for
     * @param contentType Type of content (VIDEO or DIY)
     * @param limit       Maximum number of recommendations to return
     * @return The API response or null if there was an error
     */
    private RecommendationResponse makeRecommendationRequest(final String contentId,
            final ContentType contentType, final int limit) {
        try {
            String apiUrl = UriComponentsBuilder.newInstance()
                    .scheme("http")
                    .host(recommendationApiHost)
                    .port(recommendationApiPort)
                    .path(recommendationApiPath)
                    .toUriString();
            log.info("Making recommendation request to {} for {} with ID {}", apiUrl, contentType, contentId);

            final RecommendationRequest request = RecommendationRequest.builder()
                    .contentId(contentId)
                    .contentType(contentType)
                    .userId(DEFAULT_USER_ID)
                    .limit(limit > 0 ? limit : DEFAULT_LIMIT)
                    .build();

            final ResponseEntity<RecommendationResponse> response = restTemplate.postForEntity(
                    apiUrl, request, RecommendationResponse.class);

            if (ObjectUtils.isEmpty(response) || !response.getStatusCode().is2xxSuccessful()
                    || ObjectUtils.isEmpty(response.getBody())) {
                log.warn("Unsuccessful response from recommendation API: {} for content type: {} and ID: {}",
                        response != null ? response.getStatusCode() : "null", contentType, contentId);
                return null;
            }

            // Return the body of the response
            return response.getBody();
        } catch (RestClientException ex) {
            log.warn("Error making recommendation request: {}", ex.getMessage());
            return null;
        }
    }
}
