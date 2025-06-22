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
public class VideoDetail {
    private String id;
    private String slug;
    private String title;
    private String thumbnail;
    private String videoUrl;
    private Category category;
    private List<Tag> tags;
    private int likeCount;
    private List<VideoQAItem> qaContent;
    private List<VideoCard> relatedVideos;
    private String createdAt;
}
