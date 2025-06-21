package com.betonamura.recommend.domain.video;

import com.betonamura.recommend.domain.common.ContentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for video metadata used in the recommendation algorithm.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoMetadata {
    private String videoId;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String categoryId;
    private String tags;
    private ContentType contentType;
    private Integer viewCount;
    private Integer likeCount;
}
