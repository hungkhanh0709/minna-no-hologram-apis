package com.betonamura.hologram.controller.search;

import java.util.List;

import com.betonamura.hologram.controller.Pagination;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    private String query;
    private List<ContentItem> results;
    private Pagination pagination;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ContentItem {
        private String type; // "video" or "diy"
        private Object content; // Video or Diy
    }
}
