package com.betonamura.hologram.domain.diy;

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
public class DIYCard {
    private String id;
    private String slug;
    private String title;
    private String thumbnail;
    private String summary;
    private Integer stepCount;
    private String estimatedTime;
    private String difficulty;
    private List<Tag> tags;
    private Integer likeCount;
}
