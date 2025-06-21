package com.betonamura.hologram.domain.video;

import com.betonamura.hologram.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoCard {
    private String id;
    private String slug;
    private String title;
    private String thumbnail;
    private String category;
    private List<Tag> tags;
    private Integer likeCount;
}
