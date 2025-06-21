package com.betonamura.hologram.controller.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.betonamura.hologram.config.ApiConfig;
import com.betonamura.hologram.controller.ErrorResponse;
import com.betonamura.hologram.controller.Success;
import com.betonamura.hologram.repository.like.LikeRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
public class LikeController {
    private final LikeRepository likeRepository;

    @Autowired
    public LikeController(final LikeRepository likeService) {
        this.likeRepository = likeService;
    }

    @PostMapping(ApiConfig.LIKE)
    public ResponseEntity<?> like(@Valid @RequestBody LikeRequest request, BindingResult bindingResult,
            @RequestHeader(value = "X-Client-Id", required = false) String clientId, HttpServletRequest httpRequest) {
        // Validate request body
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .findFirst().orElse("Validation failed");
            ErrorResponse errorResponse = new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Use clientId or fallback to session id
        if (clientId == null || clientId.isEmpty()) {
            clientId = httpRequest.getSession().getId();
        }

        // Check if already liked
        if (likeRepository.alreadyLiked(request.getType(), request.getId(), clientId)) {
            ErrorResponse errorResponse = new ErrorResponse(String.valueOf(HttpStatus.CONFLICT.value()),
                    "Already liked from this browser");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        }

        final boolean success = likeRepository.like(request.getType(), request.getId(), clientId);
        if (!success) {
            ErrorResponse errorResponse = new ErrorResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    "Invalid request");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return ResponseEntity.ok(new Success(true));
    }
}
