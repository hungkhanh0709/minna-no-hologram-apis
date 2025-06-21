package com.betonamura.hologram.controller.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.betonamura.hologram.controller.Pagination;
import com.betonamura.hologram.domain.diy.Diy;
import com.betonamura.hologram.domain.video.VideoCard;

@Component
public class SearchResponseFactory {

    private static final String TYPE_VIDEO = "video";
    private static final String TYPE_DIY = "diy";

    /*
     * This method constructs a list of ContentItem objects,
     * each representing either a Video or a Diy
     */
    public static SearchResponse toSearchResponse(final int offset, final int limit, final String query,
            final List<VideoCard> videos, final List<Diy> diys) {
        final List<SearchResponse.ContentItem> results = new ArrayList<>();
        for (VideoCard v : videos) {
            results.add(SearchResponse.ContentItem.builder().type(TYPE_VIDEO).content(v).build());
        }
        for (Diy d : diys) {
            results.add(SearchResponse.ContentItem.builder().type(TYPE_DIY).content(d).build());
        }

        final int totalItems = videos.size() + diys.size();
        final int pageSize = limit;
        final int page = (offset / pageSize) + 1;
        final int totalPages = (int) Math.ceil((double) totalItems / pageSize);
        return SearchResponse.builder()
                .query(query)
                .results(results)
                .pagination(Pagination.builder()
                        .page(page)
                        .pageSize(pageSize)
                        .totalItems(totalItems)
                        .totalPages(totalPages)
                        .build())
                .build();
    }
}
