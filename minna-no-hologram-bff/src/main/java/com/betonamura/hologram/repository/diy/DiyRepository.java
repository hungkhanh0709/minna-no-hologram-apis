package com.betonamura.hologram.repository.diy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

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
        public List<DiyCard> search(final int offset, final int limit,
                        final String query, final List<String> categoryIds) {

                // Fetch all DIY details
                final List<DiyDetail> diys = DiyData.getDiysDetail();
                List<DiyDetail> filtered = new ArrayList<>(diys);

                // Apply category filter (via tags)
                if (!ObjectUtils.isEmpty(categoryIds)) {
                        filtered = filtered.stream()
                                        .filter(d -> d.getTags().stream()
                                                        .anyMatch(tag -> categoryIds.contains(tag.getId())))
                                        .collect(Collectors.toList());
                }

                // Apply query filter
                if (query != null && !query.isBlank()) {
                        String lowerQuery = query.toLowerCase();
                        filtered = filtered.stream()
                                        .filter(d -> d.getTitle().toLowerCase().contains(lowerQuery) ||
                                                        d.getSummary().toLowerCase().contains(lowerQuery))
                                        .collect(Collectors.toList());
                }

                // Apply pagination
                int from = Math.max(0, offset);
                int safeLimit = Math.max(1, limit);
                int to = Math.min(filtered.size(), from + safeLimit);

                if (from >= filtered.size()) {
                        return Collections.emptyList();
                }

                return filtered.subList(from, to).stream()
                                .map(DiyData::toDiyCard)
                                .collect(Collectors.toList());
        }

        /**
         * Find a DIY project by its slug.
         *
         * @param slugId the slug identifier of the DIY project
         * @return the DiyDetail object if found, otherwise a default DIY
         */
        public DiyDetail getDiyDetail(final String slugId) {
                // Fetch all DIY details
                final List<DiyDetail> diys = DiyData.getDiysDetail();
                final DiyDetail diyDetail = diys.stream()
                                .filter(d -> d.getSlug().equalsIgnoreCase(slugId))
                                .findFirst()
                                .orElse(null);

                if (diyDetail == null) {
                        // Throw 404 Not Found if no DIY found
                        throw new IllegalArgumentException("DIY not found for slug: " + slugId);
                }

                // Get DIY ID for recommendations
                final List<DiyCard> relatedDiys = getRelatedDiys(diyDetail.getId(), diyDetail.getTags().get(0).getId());

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

        private List<DiyCard> getRelatedDiys(final String currentId, final String tagId) {
                // Get recommended DIYs from the recommendation service
                List<DiyCard> relatedDiys = recommendationRepository.getRecommendedDiys(
                                currentId, DEFAULT_RELATED_DIYS);

                // If recommendation service fails, get DIYs with the same tags
                if (ObjectUtils.isEmpty(relatedDiys)) {
                        relatedDiys = DiyData.getDiysByTag(tagId, DEFAULT_RELATED_DIYS);
                }

                return relatedDiys;
        }
}
