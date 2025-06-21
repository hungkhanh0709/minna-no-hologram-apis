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
    public HomeController(VideoRepository videoRepository, DiyRepository diyRepository) {
        this.videoRepository = videoRepository;
        this.diyRepository = diyRepository;
    }

    @GetMapping(ApiConfig.HOME)
    public ResponseEntity<HomeResponse> getHome() {
        // Use repository to get data with default offset/limit
        List<VideoCard> videos = videoRepository.search(0, 5, null, null, null);
        List<DIYCard> diys = diyRepository.search(0, 1, null, null);
        HomeResponse response = HomeResponse.builder()
                .recentVideos(videos)
                .recentDIY(diys.isEmpty() ? null : diys.get(0))
                .build();
        return ResponseEntity.ok(response);
    }
}
