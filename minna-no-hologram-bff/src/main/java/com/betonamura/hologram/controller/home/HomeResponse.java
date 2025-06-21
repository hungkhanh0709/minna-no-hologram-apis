package com.betonamura.hologram.controller.home;

import com.betonamura.hologram.domain.diy.DIYCard;
import com.betonamura.hologram.domain.video.VideoCard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeResponse {
    private List<VideoCard> recentVideos;
    private DIYCard recentDIY;
}
