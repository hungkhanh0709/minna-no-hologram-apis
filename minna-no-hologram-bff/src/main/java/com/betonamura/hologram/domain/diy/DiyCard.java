package com.betonamura.hologram.domain.diy;

import java.util.List;

import com.betonamura.hologram.domain.tag.Tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiyCard {
    private String id;
    private String slug;
    private String title;
    private String thumbnail;
    private String summary;
    private Integer stepCount;
    private String estimatedTime;
    private String difficulty;
    private List<Tag> tags;
    private int likeCount;
}
