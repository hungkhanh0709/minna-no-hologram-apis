package com.betonamura.hologram.controller.video;

import java.util.List;

import org.springframework.stereotype.Component;

import com.betonamura.hologram.controller.Pagination;
import com.betonamura.hologram.domain.video.VideoCard;

@Component
public class VideoResponseFactory {

    /**
     * Converts the provided parameters into a VideosResponse object.
     *
     * @param offset     The offset for pagination.
     * @param limit      The limit for pagination.
     * @param totalCount The total count of items.
     * @param videoCards The list of VideoCard objects to include in the response.
     * @return A VideosResponse object containing the video cards and pagination
     *         information.
     */
    public VideosResponse toVideosResponse(final int offset, final int limit,
            final int totalCount, final List<VideoCard> videoCards) {

        final int totalItems = 9999;
        final int pageSize = limit;
        final int page = (offset / pageSize) + 1;
        final int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        final Pagination pagination = Pagination.builder()
                .page(page)
                .pageSize(pageSize)
                .totalItems(totalItems)
                .totalPages(totalPages)
                .build();
        return new VideosResponse(videoCards, pagination);
    }
}
