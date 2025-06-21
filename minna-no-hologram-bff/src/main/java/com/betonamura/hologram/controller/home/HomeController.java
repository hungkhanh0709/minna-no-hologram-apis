package com.betonamura.hologram.controller.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betonamura.hologram.config.ApiConfig;
import com.betonamura.hologram.domain.diy.DIYCard;
import com.betonamura.hologram.domain.video.VideoCard;
import com.betonamura.hologram.repository.diy.DiyRepository;
import com.betonamura.hologram.repository.video.VideoRepository;

@RestController
public class HomeController {
    private final VideoRepository videoRepository;
    private final DiyRepository diyRepository;

    @Autowired
    public HomeController(final VideoRepository videoRepository, final DiyRepository diyRepository) {
        this.videoRepository = videoRepository;
        this.diyRepository = diyRepository;
    }

    /**
     * Home API endpoint.
     * <p>
     * Returns a summary for the home page, including:
     * <ul>
     *   <li>5 recent videos (with real, free-license images)</li>
     *   <li>1 recent DIY article</li>
     * </ul>
     * Data is fetched from repositories with default offset/limit values.
     *
     * @return HomeResponse containing recent videos and a DIY article
     */
    @GetMapping(ApiConfig.HOME)
    public ResponseEntity<HomeResponse> getHome() {
        // Use repository to get data with default offset/limit
        final List<VideoCard> videos = videoRepository.search(0, 5, null, null, null);
        final List<DIYCard> diys = diyRepository.search(0, 1, null, null);
        final HomeResponse response = HomeResponse.builder()
                .recentVideos(videos)
                .recentDIY(diys.isEmpty() ? null : diys.get(0))
                .build();
        return ResponseEntity.ok(response);
    }
}
