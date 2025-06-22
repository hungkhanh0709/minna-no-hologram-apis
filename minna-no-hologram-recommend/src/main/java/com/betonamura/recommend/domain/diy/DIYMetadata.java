package com.betonamura.recommend.domain.diy;

import com.betonamura.recommend.domain.common.ContentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model for DIY content metadata used in the recommendation algorithm.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DIYMetadata {
    private String diyId;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String categoryId;
    private String tags;
    private ContentType contentType;
    private Integer difficulty;
    private Integer viewCount;
    private Integer likeCount;
}
