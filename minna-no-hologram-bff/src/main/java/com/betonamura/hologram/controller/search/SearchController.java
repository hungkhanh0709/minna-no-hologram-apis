package com.betonamura.hologram.controller.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betonamura.hologram.config.ApiConfig;
import com.betonamura.hologram.controller.ErrorResponse;
import com.betonamura.hologram.domain.diy.Diy;
import com.betonamura.hologram.domain.video.Video;
import com.betonamura.hologram.repository.diy.DiyRepository;
import com.betonamura.hologram.repository.video.VideoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping
public class SearchController {
    private final VideoRepository videoRepository;
    private final DiyRepository diyRepository;

    @Autowired
    public SearchController(final VideoRepository videoRepository, final DiyRepository diyRepository) {
        this.videoRepository = videoRepository;
        this.diyRepository = diyRepository;
    }

    @GetMapping(ApiConfig.SEARCH)
    public ResponseEntity<?> search(@Valid @ModelAttribute SearchRequest request, BindingResult bindingResult) {

        // Validate the request parameters
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .findFirst().orElse("Validation failed");
            ErrorResponse errorResponse = new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Search videos and DIYs
        final List<Video> videos = videoRepository.search(request.getOffset(), request.getLimit(), request.getP());
        final List<Diy> diys = diyRepository.search(request.getOffset(), request.getLimit(), request.getP());

        final SearchResponse response = SearchResponseFactory.toSearchResponse(
                request.getOffset(), request.getLimit(), request.getP(),
                videos, diys);
        return ResponseEntity.ok(response);
    }
}
