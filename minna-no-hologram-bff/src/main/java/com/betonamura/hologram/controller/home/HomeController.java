package com.betonamura.hologram.controller.home;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betonamura.hologram.config.ApiConfig;
import com.betonamura.hologram.domain.diy.Diy;
import com.betonamura.hologram.domain.video.Video;
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
     * Returns a summary for the home page, including:
     * Data is fetched from repositories with default offset/limit values.
     *
     * @return HomeResponse containing recent videos and a DIY article
     */
    @GetMapping(ApiConfig.HOME)
    public ResponseEntity<HomeResponse> getHome() {
        // Use repository to get data with default offset/limit
        final List<Video> videos = videoRepository.search(0, 5);
        final List<Diy> diys = diyRepository.search(0, 1);
        final HomeResponse response = HomeResponse.builder()
                .recentVideos(videos)
                .recentDIY(diys.isEmpty() ? null : diys.get(0))
                .build();
        return ResponseEntity.ok(response);
    }
}
