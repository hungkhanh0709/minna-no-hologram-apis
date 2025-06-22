package com.betonamura.hologram.repository.diy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.betonamura.hologram.domain.diy.DiyCard;
import com.betonamura.hologram.domain.diy.DiyDetail;
import com.betonamura.hologram.repository.diy.data.DiyData;
import com.betonamura.hologram.repository.recommend.RecommendationRepository;

/**
 * Repository for accessing DIY project data
 */
@Repository
public class DiyRepository {

        private final RecommendationRepository recommendationRepository;
        private static final int MAX_LIMIT = 50; // Maximum items per page
        private static final int DEFAULT_RELATED_DIYS = 2; // Default number of related DIYs

        @Autowired
        public DiyRepository(RecommendationRepository recommendationRepository) {
                this.recommendationRepository = recommendationRepository;
        }

        /**
         * Search for DIY projects with pagination.
         * 
         * @param offset The offset for pagination
         * @param limit  The number of items per page
         * @return A list of DIY cards
         */
        public List<DiyCard> search(final int offset, final int limit) {
                return search(offset, limit, null, null);
        }

        /**
         * Search for DIY projects with pagination and a query filter.
         * 
         * @param offset The offset for pagination
         * @param limit  The number of items per page
         * @param query  The search query
         * @return A list of filtered DIY cards
         */
        public List<DiyCard> search(final int offset, final int limit, final String query) {
                return search(offset, limit, query, null);
        }

        /**
         * Search for DIY projects with pagination and category filtering.
         * 
         * @param offset      The offset for pagination
         * @param limit       The number of items per page
         * @param categoryIds The list of category IDs to filter by
         * @return A list of filtered DIY cards
         */
        public List<DiyCard> search(final int offset, final int limit, final List<String> categoryIds) {
                return search(offset, limit, null, categoryIds);
        }

        /**
         * Searches for DIY projects with comprehensive filtering options.
         * 
         * @param offset      The starting index for pagination
         * @param limit       The maximum number of results to return
         * @param query       The search keyword (optional)
         * @param categoryIds List of category IDs to filter by (optional)
         * @return A list of DIY projects matching the search criteria
         */
        public List<DiyCard> search(final int offset, final int limit, final String query,
                        final List<String> categoryIds) {
                // int safeLimit = Math.max(1, Math.min(limit, MAX_LIMIT));
                // return DiyData.getPaginatedDiys(offset, safeLimit, categoryIds, query);

                return new ArrayList<>(); // Placeholder for actual implementation
        }

        /**
         * Find a DIY project by its slug.
         *
         * @param slugId the slug identifier of the DIY project
         * @return the DiyDetail object if found, otherwise a default DIY
         */
        public DiyDetail getDiyDetail(final String slugId) {
                if (slugId == null || slugId.isBlank()) {
                        // Return the first DIY if no slug provided
                        return DiyData.getDiyDetailBySlug(DiyData.getPaginatedDiys(0, 1, null, null).get(0).getSlug());
                }

                // Try to get the DIY from our data
                DiyDetail diyDetail = DiyData.getDiyDetailBySlug(slugId);
                if (diyDetail == null) {
                        // If not found, return the first DIY
                        return DiyData.getDiyDetailBySlug(DiyData.getPaginatedDiys(0, 1, null, null).get(0).getSlug());
                }

                // Get DIY ID for recommendations
                String diyId = diyDetail.getId();

                // Get recommended DIYs from the recommendation service
                List<DiyCard> relatedDiys = recommendationRepository.getRecommendedDiysByDiy(diyId,
                                DEFAULT_RELATED_DIYS);

                // If recommendation service fails, get DIYs with the same tags
                if (relatedDiys == null || relatedDiys.isEmpty()) {
                        // Get DIYs with the same tags as fallback
                        String primaryTag = diyDetail.getTags().get(0).getId(); // Use first tag as primary
                        relatedDiys = DiyData.getDiysByTag(primaryTag, DEFAULT_RELATED_DIYS);
                }

                // Create a new DiyDetail with updated related DIYs
                return DiyDetail.builder()
                                .id(diyDetail.getId())
                                .slug(diyDetail.getSlug())
                                .title(diyDetail.getTitle())
                                .videoUrl(diyDetail.getVideoUrl())
                                .summary(diyDetail.getSummary())
                                .stepCount(diyDetail.getStepCount())
                                .estimatedTime(diyDetail.getEstimatedTime())
                                .difficulty(diyDetail.getDifficulty())
                                .tags(diyDetail.getTags())
                                .likeCount(diyDetail.getLikeCount())
                                .materials(diyDetail.getMaterials())
                                .steps(diyDetail.getSteps())
                                .relatedDIY(relatedDiys)
                                .createdAt(diyDetail.getCreatedAt())
                                .build();
        }
}
