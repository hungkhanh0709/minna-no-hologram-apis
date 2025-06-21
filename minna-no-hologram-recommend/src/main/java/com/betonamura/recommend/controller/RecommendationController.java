package com.betonamura.recommend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betonamura.recommend.config.ApiConfig;
import com.betonamura.recommend.service.RecommendationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for recommendation API endpoints.
 */
@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Recommendation API", description = "API endpoints for content recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    /**
     * Get related content based on the current item's category (video or DIY).
     * For video category, returns related videos. For DIY category, returns related
     * DIYs.
     */
    @GetMapping(ApiConfig.RECOMMENDATIONS)
    @Operation(summary = "Get related content", description = "Returns related content (videos or DIYs) based on the current item's ID and category. "
            +
            "For video category, returns related videos. For DIY category, returns related DIYs.")
    public ResponseEntity<?> getRecommendations(@Valid @ModelAttribute RecommendationRequest request,
            BindingResult bindingResult) {
        log.info("Received recommendation request: {}", request);

        // Validate request parameters
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .findFirst().orElse("Validation failed");

            log.warn("Validation failed: {}", message);
            return ResponseEntity.badRequest().body(ErrorResponse.of(message));
        }

        try {
            RecommendationResponse response = recommendationService.getRecommendations(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting recommendations", e);
            return ResponseEntity.internalServerError()
                    .body(ErrorResponse.of("Failed to get recommendations", e.getMessage()));
        }
    }

}
