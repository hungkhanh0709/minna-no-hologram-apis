package com.betonamura.hologram.domain.video;

import java.util.List;

import com.betonamura.hologram.domain.category.Category;
import com.betonamura.hologram.domain.tag.Tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoCard {
    private String id;
    private String slug;
    private String title;
    private String thumbnail;
    private String summary;
    private Category category;
    private List<Tag> tags;
    private int likeCount;
}
