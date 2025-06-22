package com.betonamura.recommend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betonamura.recommend.config.ApiConfig;
import com.betonamura.recommend.repository.RecommendationRepositoryImpl;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for recommendation API endpoints.
 */
@Slf4j
@RequestMapping
@RestController
public class RecommendationController {

    private final RecommendationRepositoryImpl recommendRepository;

    @Autowired
    public RecommendationController(final RecommendationRepositoryImpl recommendRepository) {
        this.recommendRepository = recommendRepository;
    }

    /**
     * Get related content based on the current item's category (video or DIY).
     * For video category, returns related videos. For DIY category, returns related
     * DIYs.
     */
    @GetMapping(ApiConfig.RECOMMENDATIONS)
    public ResponseEntity<?> getRecommendations(@Valid @ModelAttribute RecommendationRequest request,
            BindingResult bindingResult) {

        // Validate request parameters
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .findFirst().orElse("Validation failed");

            log.warn("Validation failed: {}", message);
            return ResponseEntity.badRequest().body(ErrorResponse.of(message));
        }

        try {
            RecommendationResponse response = recommendRepository.getRecommendations(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting recommendations", e);
            return ResponseEntity.internalServerError()
                    .body(ErrorResponse.of("Failed to get recommendations", e.getMessage()));
        }
    }
}
