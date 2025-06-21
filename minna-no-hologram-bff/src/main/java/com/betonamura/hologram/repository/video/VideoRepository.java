package com.betonamura.hologram.repository.video;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.betonamura.hologram.domain.tag.Tag;
import com.betonamura.hologram.domain.video.Video;

@Repository
public class VideoRepository {

    /**
     * Search for videos with pagination support.
     * 
     * @param offset The starting index for pagination
     * @param limit  The maximum number of videos to return
     * @return List of Video objects representing the search results
     */
    public List<Video> search(final int offset, final int limit) {
        // Dummy implementation for search functionality
        List<Video> videos = this.getVideos();

        // Apply offset and limit
        int from = Math.max(0, offset);
        int to = Math.min(videos.size(), from + Math.max(1, Math.min(limit, 50)));
        return videos.subList(from, to);
    }

    /**
     * Get a list of dummy videos for the home page.
     * 
     * @return List of VideoCard objects representing recent videos
     */
    private List<Video> getVideos() {
        // Return 5 dummy videos for home page, support offset/limit
        Tag physicsTag = Tag.builder().id("physics").name("Physics").build();
        Tag makerTag = Tag.builder().id("maker").name("Maker").build();

        final Video video1 = Video.builder()
                .id("123")
                .slug("quantum-physics-explained")
                .title("Quantum Physics Explained with Holograms")
                .thumbnail("https://images.unsplash.com/photo-1464983953574-0892a716854b")
                .category("science")
                .tags(List.of(physicsTag))
                .likeCount(1250)
                .build();

        Video video2 = Video.builder()
                .id("128")
                .slug("hologram-technology-in-education")
                .title("Hologram Technology in Education")
                .thumbnail("https://images.unsplash.com/photo-1506748686214-e9df14d4d9f3")
                .category("education")
                .tags(List.of(physicsTag, makerTag))
                .likeCount(1100)
                .build();

        Video video3 = Video.builder()
                .id("125")
                .slug("diy-hologram-projects")
                .title("Top 5 DIY Hologram Projects")
                .thumbnail("https://images.unsplash.com/photo-1506744038136-46273834b3fb")
                .category("maker")
                .tags(List.of(makerTag))
                .likeCount(870)
                .build();

        Video video4 = Video.builder()
                .id("126")
                .slug("hologram-in-pop-culture")
                .title("Holograms in Pop Culture")
                .thumbnail("https://images.unsplash.com/photo-1465101046530-73398c7f28ca")
                .category("entertainment")
                .tags(List.of(physicsTag, makerTag))
                .likeCount(650)
                .build();

        Video video5 = Video.builder()
                .id("127")
                .slug("future-of-holograms")
                .title("The Future of Hologram Technology")
                .thumbnail("https://images.unsplash.com/photo-1465101178521-c1a9136a3b99")
                .category("technology")
                .tags(List.of(physicsTag))
                .likeCount(540)
                .build();

        // Simulate a search with offset and limit
        List<Video> videos = new ArrayList<>();
        videos.add(video1);
        videos.add(video2);
        videos.add(video3);
        videos.add(video4);
        videos.add(video5);

        return videos;
    }
}
