package com.betonamura.hologram.repository.recommend;

import java.util.List;

import com.betonamura.hologram.domain.diy.DiyCard;
import com.betonamura.hologram.domain.video.VideoCard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponse {
    private List<VideoCard> videos;
    private List<DiyCard> diys;
}
