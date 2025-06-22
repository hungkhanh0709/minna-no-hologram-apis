package com.betonamura.hologram.repository.recommend;

import java.util.List;

import com.betonamura.hologram.domain.diy.DiyCard;
import com.betonamura.hologram.domain.video.VideoCard;

/**
 * Interface for getting content recommendations from the recommendation
 * service.
 */
public interface RecommendationRepository {

    /**
     * Get recommended videos for a given video ID.
     *
     * @param videoId the ID of the video
     * @param limit   the maximum number of recommendations to return
     * @return a list of recommended video cards, empty list if error
     */
    List<VideoCard> getRecommendedVideos(String videoId, int limit);

    /**
     * Get recommended DIY projects for a given video ID.
     *
     * @param videoId the ID of the video
     * @param limit   the maximum number of recommendations to return
     * @return a list of recommended DIY cards, empty list if error
     */
    List<DiyCard> getRecommendedDiys(String videoId, int limit);

    /**
     * Get recommended videos for a given DIY project ID.
     *
     * @param diyId the ID of the DIY project
     * @param limit the maximum number of recommendations to return
     * @return a list of recommended video cards, empty list if error
     */
    List<VideoCard> getRecommendedVideosByDiy(String diyId, int limit);

    /**
     * Get recommended DIY projects for a given DIY project ID.
     *
     * @param diyId the ID of the DIY project
     * @param limit the maximum number of recommendations to return
     * @return a list of recommended DIY cards, empty list if error
     */
    List<DiyCard> getRecommendedDiysByDiy(String diyId, int limit);
}
