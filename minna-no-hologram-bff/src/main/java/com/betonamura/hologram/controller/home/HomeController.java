package com.betonamura.hologram.controller.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betonamura.hologram.config.ApiConfig;
import com.betonamura.hologram.domain.diy.DiyCard;
import com.betonamura.hologram.domain.video.VideoCard;
import com.betonamura.hologram.repository.diy.DiyRepository;
import com.betonamura.hologram.repository.video.VideoRepository;

@RestController
public class HomeController {
    private static final int DEFAULT_OFFSET = 0; // Default offset for pagination
    private static final int DEFAULT_DIY_LIMIT = 1; // Default number of DIY articles
    private static final int DEFAULT_VIDEO_LIMIT = 5; // Default number of videos to fetch

    private final VideoRepository videoRepository;
    private final DiyRepository diyRepository;

    @Autowired
    public HomeController(final VideoRepository videoRepository, final DiyRepository diyRepository) {
        this.videoRepository = videoRepository;
        this.diyRepository = diyRepository;
    }

    /**
     * Returns a summary for the home page, including:
     * Data is fetched from repositories with default offset/limit values.
     *
     * @return HomeResponse containing recent videos and a DIY article
     */
    @GetMapping(ApiConfig.HOME)
    public ResponseEntity<HomeResponse> getHome() {
        // Use repository to get data with default offset/limit
        final List<VideoCard> videos = videoRepository.search(DEFAULT_OFFSET, DEFAULT_VIDEO_LIMIT);
        final List<DiyCard> diys = diyRepository.search(DEFAULT_OFFSET, DEFAULT_DIY_LIMIT);
        final HomeResponse response = HomeResponse.builder()
                .recentVideos(videos)
                .recentDIY(diys.isEmpty() ? null : diys.get(0))
                .build();
        return ResponseEntity.ok(response);
    }
}
