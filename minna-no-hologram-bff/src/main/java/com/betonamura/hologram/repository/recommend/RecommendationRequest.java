package com.betonamura.hologram.repository.recommend;

import com.betonamura.hologram.domain.ContentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationRequest {
    private String userId;
    private ContentType contentType;
    private String contentId;
    private int limit;
}
