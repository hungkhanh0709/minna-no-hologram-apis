package com.betonamura.hologram.repository.diy;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.betonamura.hologram.domain.Tag;
import com.betonamura.hologram.domain.diy.DIYCard;

@Repository
public class DiyRepository {
    public List<DIYCard> search(int offset, int limit, String keyword, List<Tag> tags) {
        // Return 1 dummy DIY for home page, support offset/limit
        DIYCard diy = DIYCard.builder()
                .id("456")
                .slug("build-pyramid-hologram-projector")
                .title("Build Your Own Pyramid Hologram Projector")
                .thumbnail("https://example.com/thumbnails/pyramid-hologram.jpg")
                .summary("Learn how to build a simple pyramid hologram projector using household materials.")
                .stepCount(5)
                .estimatedTime("10-15 min")
                .difficulty("easy")
                .tags(List.of(Tag.builder().id("maker").name("Maker").build()))
                .likeCount(750)
                .build();
        List<DIYCard> all = List.of(diy);
        int from = Math.max(0, offset);
        int to = Math.min(all.size(), from + Math.max(1, Math.min(limit, 50)));
        return all.subList(from, to);
    }
}
