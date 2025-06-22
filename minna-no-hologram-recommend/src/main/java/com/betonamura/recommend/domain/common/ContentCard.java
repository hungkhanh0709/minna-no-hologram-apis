package com.betonamura.recommend.domain.common;

import java.util.List;

import com.betonamura.recommend.domain.tag.Tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Base class for both VideoCard and DiyCard.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class ContentCard {
    private String id;
    private String slug;
    private String title;
    private String thumbnail;
    private String category;
    private List<Tag> tags;
    private int likeCount;
    private Double similarityScore;
}
