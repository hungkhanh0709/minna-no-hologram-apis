package com.betonamura.hologram.domain.diy;

import java.util.List;

import com.betonamura.hologram.domain.tag.Tag;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiyDetail {
    private String id;
    private String slug;
    private String title;
    private String videoUrl;
    private String summary;
    private Integer stepCount;
    private String estimatedTime;
    private String difficulty;
    private List<Tag> tags;
    private int likeCount;
    private List<Material> materials;
    private List<DIYStep> steps;
    private List<DiyCard> relatedDIY;
    private java.time.OffsetDateTime createdAt;
}
