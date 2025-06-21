package com.betonamura.hologram.controller.video;

import java.util.List;

import com.betonamura.hologram.controller.Pagination;
import com.betonamura.hologram.domain.video.VideoCard;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VideosResponse {
    private List<VideoCard> results;
    private Pagination pagination;
}
