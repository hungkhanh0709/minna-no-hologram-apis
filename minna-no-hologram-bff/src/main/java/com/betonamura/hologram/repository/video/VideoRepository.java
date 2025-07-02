package com.betonamura.hologram.repository.video;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.betonamura.hologram.domain.video.VideoCard;
import com.betonamura.hologram.domain.video.VideoDetail;
import com.betonamura.hologram.repository.recommend.RecommendationRepository;
import com.betonamura.hologram.repository.video.data.VideoData;

/**
 * Repository for accessing video content data
 */
@Repository
public class VideoRepository {

        private final RecommendationRepository recommendationRepository;
        private static final int DEFAULT_RELATED_VIDEOS = 2; // Default number of related videos

        @Autowired
        public VideoRepository(RecommendationRepository recommendationRepository) {
                this.recommendationRepository = recommendationRepository;
        }

        /**
         * Search for videos with pagination.
         * 
         * @param offset The offset for pagination
         * @param limit  The number of items per page
         * @return A list of video cards
         */
        public List<VideoCard> search(final int offset, final int limit) {
                return search(offset, limit, null, null);
        }

        /**
         * Search for videos with pagination and a query filter.
         * 
         * @param offset The offset for pagination
         * @param limit  The number of items per page
         * @param query  The search query
         * @return A list of filtered video cards
         */
        public List<VideoCard> search(final int offset, final int limit, final String query) {
                return search(offset, limit, query, null);
        }

        /**
         * Search for videos with pagination and category filtering.
         * 
         * @param offset      The offset for pagination
         * @param limit       The number of items per page
         * @param categoryIds The list of category IDs to filter by
         * @return A list of filtered video cards
         */
        public List<VideoCard> search(final int offset, final int limit, final List<String> categoryIds) {
                return search(offset, limit, null, categoryIds);
        }

        /**
         * Searches for videos with comprehensive filtering options.
         * 
         * @param offset      The starting index for pagination
         * @param limit       The maximum number of results to return
         * @param query       The search keyword (optional)
         * @param categoryIds List of category IDs to filter by (optional)
         * @return A list of video cards matching the search criteria
         */
        public List<VideoCard> search(final int offset, final int limit,
                        final String query, final List<String> categoryIds) {
                // return VideoData.searchVideos(offset, limit, categoryIds, query);

                final List<VideoDetail> allVideos = VideoData.getVideosDetail();
                List<VideoDetail> filtered = new ArrayList<>(allVideos);

                // Apply category filter
                if (!ObjectUtils.isEmpty(categoryIds)) {
                        filtered = filtered.stream()
                                        .filter(v -> categoryIds.contains(v.getCategory().getId()))
                                        .collect(Collectors.toList());
                }

                // Apply query filter
                if (StringUtils.hasText(query)) {
                        String lowerQuery = query.toLowerCase();
                        filtered = filtered.stream()
                                        .filter(v -> v.getTitle().toLowerCase().contains(lowerQuery))
                                        .collect(Collectors.toList());
                }

                // Apply pagination
                final int from = Math.max(0, offset);
                final int safeLimit = Math.max(1, limit);
                final int to = Math.min(filtered.size(), from + safeLimit);
                if (from >= filtered.size()) {
                        return Collections.emptyList();
                }

                // Result
                return filtered.subList(from, to).stream()
                                .map(VideoData::toVideoCard)
                                .collect(Collectors.toList());
        }

        /**
         * Find a video by its slug.
         *
         * @param slugId the slug identifier of the video
         * @return the VideoDetail object if found, otherwise a default video
         */
        public VideoDetail getVideoDetail(final String slugId) {
                // Try to get the video from our data
                VideoDetail videoDetail = VideoData.getVideoDetailBySlug(slugId);
                if (videoDetail == null) {
                        // Throw bad request if no video found
                        throw new IllegalArgumentException("Video not found for slug: " + slugId);
                }

                // Get recommended videos from the recommendation service
                List<VideoCard> relatedVideos = this.getRelatedVideos(videoDetail.getId(),
                                videoDetail.getCategory().getId());

                // Create a new VideoDetail with updated related videos
                return VideoDetail.builder()
                                .id(videoDetail.getId())
                                .slug(videoDetail.getSlug())
                                .title(videoDetail.getTitle())
                                .thumbnail(videoDetail.getThumbnail())
                                .summary(videoDetail.getSummary())
                                .videoUrl(videoDetail.getVideoUrl())
                                .category(videoDetail.getCategory())
                                .tags(videoDetail.getTags())
                                .likeCount(videoDetail.getLikeCount())
                                .qaContent(videoDetail.getQaContent())
                                .relatedVideos(relatedVideos)
                                .createdAt(videoDetail.getCreatedAt())
                                .build();
        }

        /**
         * Get related videos for a given video ID.
         *
         * @param currentId the ID of the current video
         * @return a list of related video cards
         */
        private List<VideoCard> getRelatedVideos(final String currentId, final String cagegoryId) {
                // Get recommended videos from the recommendation service
                List<VideoCard> relatedVideos = recommendationRepository.getRecommendedVideos(
                                currentId, DEFAULT_RELATED_VIDEOS);

                // If recommendation service fails, get videos of the same category
                if (ObjectUtils.isEmpty(relatedVideos)) {
                        relatedVideos = VideoData.getVideosByCategory(currentId, cagegoryId, DEFAULT_RELATED_VIDEOS);
                }
                return relatedVideos;
        }
}
