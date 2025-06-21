package com.betonamura.hologram.repository.video;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.betonamura.hologram.domain.Tag;
import com.betonamura.hologram.domain.video.VideoCard;

@Repository
public class VideoRepository {
    public List<VideoCard> search(final int offset, final int limit, final String keyword, final String category, final List<Tag> tags) {
        // Return 5 dummy videos for home page, support offset/limit
        Tag physicsTag = Tag.builder().id("physics").name("Physics").build();
        Tag makerTag = Tag.builder().id("maker").name("Maker").build();
        List<VideoCard> all = List.of(
            VideoCard.builder()
                .id("123")
                .slug("quantum-physics-explained")
                .title("Quantum Physics Explained with Holograms")
                .thumbnail("https://images.unsplash.com/photo-1464983953574-0892a716854b")
                .category("science")
                .tags(List.of(physicsTag))
                .likeCount(1250)
                .build(),
            VideoCard.builder()
                .id("124")
                .slug("hologram-history")
                .title("The History of Holograms")
                .thumbnail("https://images.unsplash.com/photo-1519125323398-675f0ddb6308")
                .category("history")
                .tags(List.of(makerTag))
                .likeCount(980)
                .build(),
            VideoCard.builder()
                .id("125")
                .slug("diy-hologram-projects")
                .title("Top 5 DIY Hologram Projects")
                .thumbnail("https://images.unsplash.com/photo-1506744038136-46273834b3fb")
                .category("maker")
                .tags(List.of(makerTag))
                .likeCount(870)
                .build(),
            VideoCard.builder()
                .id("126")
                .slug("hologram-in-pop-culture")
                .title("Holograms in Pop Culture")
                .thumbnail("https://images.unsplash.com/photo-1465101046530-73398c7f28ca")
                .category("entertainment")
                .tags(List.of(physicsTag, makerTag))
                .likeCount(650)
                .build(),
            VideoCard.builder()
                .id("127")
                .slug("future-of-holograms")
                .title("The Future of Hologram Technology")
                .thumbnail("https://images.unsplash.com/photo-1465101178521-c1a9136a3b99")
                .category("technology")
                .tags(List.of(physicsTag))
                .likeCount(540)
                .build()
        );
        // Apply offset and limit
        int from = Math.max(0, offset);
        int to = Math.min(all.size(), from + Math.max(1, Math.min(limit, 50)));
        return all.subList(from, to);
    }
}
