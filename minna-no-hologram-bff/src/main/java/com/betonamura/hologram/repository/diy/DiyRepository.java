package com.betonamura.hologram.repository.diy;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.betonamura.hologram.domain.diy.Diy;
import com.betonamura.hologram.domain.tag.Tag;

@Repository
public class DiyRepository {

    /**
     * Searches for DIY projects based on the provided keyword, offset, and limit.
     * 
     * @param offset  The starting index for pagination.
     * @param limit   The maximum number of results to return.
     * @param keyword The search keyword (not used in this dummy implementation).
     * @return A list of DIY projects matching the search criteria.
     */
    public List<Diy> search(final int offset, final int limit, final String query) {
        // Tag
        final Tag makerTag = Tag.builder().id("maker").name("Maker").build();
        final List<Tag> tags = List.of(makerTag);

        // Return 1 dummy DIY for home page, support offset/limit
        final Diy diy = Diy.builder()
                .id("456")
                .slug("build-pyramid-hologram-projector")
                .title("Build Your Own Pyramid Hologram Projector")
                .thumbnail("https://example.com/thumbnails/pyramid-hologram.jpg")
                .summary("Learn how to build a simple pyramid hologram projector using household materials.")
                .stepCount(5)
                .estimatedTime("10-15 min")
                .difficulty("easy")
                .tags(tags)
                .likeCount(750)
                .build();

        // Simulate a search with offset and limit
        List<Diy> all = List.of(diy);
        // Filter by query if present
        if (query != null && !query.isBlank()) {
            all = all.stream()
                    .filter(d -> d.getTitle() != null && d.getTitle().toLowerCase().contains(query.toLowerCase()))
                    .toList();
        }
        int from = Math.max(0, offset);
        int to = Math.min(all.size(), from + Math.max(1, Math.min(limit, 50)));
        return all.subList(from, to);
    }
}
