package com.betonamura.hologram.controller.home;

import java.util.List;

import com.betonamura.hologram.domain.diy.Diy;
import com.betonamura.hologram.domain.video.VideoCard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeResponse {
    private List<VideoCard> recentVideos;
    private Diy recentDIY;
}
