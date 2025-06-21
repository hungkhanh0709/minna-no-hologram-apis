package com.betonamura.hologram.controller.video;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import com.betonamura.hologram.config.ApiConfig;
import com.betonamura.hologram.controller.ErrorResponse;
import com.betonamura.hologram.domain.video.VideoCard;
import com.betonamura.hologram.domain.video.VideoDetail;
import com.betonamura.hologram.repository.video.VideoRepository;

import jakarta.validation.Valid;

@RestController
public class VideoController {

    private final VideoRepository videoRepository;
    private final VideoResponseFactory videoResponseFactory;

    @Autowired
    public VideoController(final VideoRepository videoRepository,
            final VideoResponseFactory videoResponseFactory) {
        this.videoRepository = videoRepository;
        this.videoResponseFactory = videoResponseFactory;
    }

    @GetMapping(ApiConfig.VIDEO_SEARCH)
    public ResponseEntity<?> search(@Valid @ModelAttribute VideosRequest request, BindingResult bindingResult) {
        // Validate the request parameters
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .findFirst().orElse("Validation failed");
            ErrorResponse errorResponse = new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Fetch videos by category and pagination
        final List<VideoCard> videos = videoRepository.search(request.getOffset(), request.getLimit(),
                request.getCategoryIds());

        // Refactor the response creation
        final VideosResponse response = videoResponseFactory.toVideosResponse(
                request.getOffset(), request.getLimit(), 9999, videos);

        return ResponseEntity.ok(response);
    }

    @GetMapping(ApiConfig.VIDEO_DETAIL)
    public ResponseEntity<?> getVideoDetail(@Valid @ModelAttribute VideoDetailRequest request,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .findFirst().orElse("Validation failed");
            ErrorResponse errorResponse = new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        String slugId = request.getSlugId();
        final VideoDetail video = videoRepository.getVideoDetail(slugId);
        if (video == null) {
            ErrorResponse errorResponse = new ErrorResponse(String.valueOf(HttpStatus.NOT_FOUND.value()),
                    "Video not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
        return ResponseEntity.ok(video);
    }
}
