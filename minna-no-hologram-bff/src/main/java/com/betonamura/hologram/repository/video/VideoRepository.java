package com.betonamura.hologram.repository.video;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.betonamura.hologram.domain.category.Category;
import com.betonamura.hologram.domain.qa.QAItem;
import com.betonamura.hologram.domain.tag.Tag;
import com.betonamura.hologram.domain.video.VideoCard;
import com.betonamura.hologram.domain.video.VideoDetail;

@Repository
public class VideoRepository {
        public List<VideoCard> search(final int offset, final int limit) {
                return search(offset, limit, null, null);
        }

        public List<VideoCard> search(final int offset, final int limit, final String query) {
                return search(offset, limit, query, null);
        }

        public List<VideoCard> search(final int offset, final int limit, final List<String> getCategoryIds) {
                return search(offset, limit, "", getCategoryIds);
        }

        public List<VideoCard> search(final int offset, final int limit, final String query,
                        final List<String> getCategoryIds) {
                List<VideoCard> videos = this.getVideos();
                // Filter by category if present
                if (getCategoryIds != null && !getCategoryIds.isEmpty()) {
                        videos = videos.stream()
                                        .filter(v -> v.getCategory() != null
                                                        && getCategoryIds.contains(v.getCategory()))
                                        .toList();
                }
                int from = Math.max(0, offset);
                int to = Math.min(videos.size(), from + Math.max(1, Math.min(limit, 50)));
                return videos.subList(from, to);
        }

        /**
         * Find a video by its slug.
         *
         * @param slugId the slug identifier of the video
         * @return the Video object if found, otherwise null
         */
        public VideoDetail getVideoDetail(final String slugId) {
                // Use builder pattern and helper methods for clarity
                return VideoDetail.builder()
                                .id("123")
                                .slug(slugId)
                                .title("Quantum Physics Explained with Holograms")
                                .videoUrl("https://example.com/videos/" + slugId + ".mp4")
                                .category(buildCategory())
                                .tags(buildTags())
                                .likeCount(1250)
                                .qaContent(buildQAContent())
                                .relatedVideos(buildRelatedVideos())
                                .createdAt("2023-10-01T12:00:00Z")
                                .build();
        }

        private Category buildCategory() {
                return Category.builder()
                                .id("science")
                                .name("Science")
                                .description("Scientific topics and discoveries")
                                .build();
        }

        private List<Tag> buildTags() {
                return List.of(Tag.builder().id("physics").name("Physics").build());
        }

        private List<QAItem> buildQAContent() {
                // Create dummy QA content for the video
                final QAItem qaItem1 = QAItem.builder()
                                .question("What is quantum physics?")
                                .answer("Quantum physics is the branch of physics that deals with the behavior of matter and light on the atomic and subatomic scale.")
                                .build();

                final QAItem qaItem2 = QAItem.builder()
                                .question("How do holograms work?")
                                .answer("Holograms are created by recording light patterns reflected from an object, allowing 3D images to be viewed from different angles.")
                                .build();

                final QAItem qaItem3 = QAItem.builder()
                                .question("What are some applications of holograms?")
                                .answer("Holograms are used in various fields including data storage, security, and medical imaging.")
                                .build();

                final QAItem qaItem4 = QAItem.builder()
                                .question("Can holograms be used in education?")
                                .answer("Yes, holograms can enhance learning experiences by providing interactive 3D visualizations.")
                                .build();

                return List.of(qaItem1, qaItem2, qaItem3, qaItem4);
        }

        private List<VideoCard> buildRelatedVideos() {
                final VideoCard relatedVideo1 = VideoCard.builder()
                                .id("128")
                                .slug("hologram-technology-in-education")
                                .title("Hologram Technology in Education")
                                .thumbnail("https://images.unsplash.com/photo-1506748686214-e9df14d4d9f3")
                                .category("education")
                                .tags(buildTags())
                                .likeCount(1100)
                                .build();
                final VideoCard relatedVideo2 = VideoCard.builder()
                                .id("126")
                                .slug("hologram-in-pop-culture")
                                .title("Holograms in Pop Culture")
                                .thumbnail("https://images.unsplash.com/photo-1465101046530-73398c7f28ca")
                                .category("entertainment")
                                .tags(buildTags())
                                .likeCount(650)
                                .build();

                return List.of(relatedVideo1, relatedVideo2);
        }

        /**
         * Get a list of dummy videos for the home page.
         * 
         * @return List of VideoCard objects representing recent videos
         */
        private List<VideoCard> getVideos() {
                // Return 5 dummy videos for home page, support offset/limit
                Tag physicsTag = Tag.builder().id("physics").name("Physics").build();
                Tag makerTag = Tag.builder().id("maker").name("Maker").build();

                final VideoCard video1 = VideoCard.builder()
                                .id("123")
                                .slug("quantum-physics-explained")
                                .title("Quantum Physics Explained with Holograms")
                                .thumbnail("https://images.unsplash.com/photo-1464983953574-0892a716854b")
                                .category("science")
                                .tags(List.of(physicsTag))
                                .likeCount(1250)
                                .build();

                VideoCard video2 = VideoCard.builder()
                                .id("128")
                                .slug("hologram-technology-in-education")
                                .title("Hologram Technology in Education")
                                .thumbnail("https://images.unsplash.com/photo-1506748686214-e9df14d4d9f3")
                                .category("education")
                                .tags(List.of(physicsTag, makerTag))
                                .likeCount(1100)
                                .build();

                VideoCard video3 = VideoCard.builder()
                                .id("125")
                                .slug("diy-hologram-projects")
                                .title("Top 5 DIY Hologram Projects")
                                .thumbnail("https://images.unsplash.com/photo-1506744038136-46273834b3fb")
                                .category("maker")
                                .tags(List.of(makerTag))
                                .likeCount(870)
                                .build();

                VideoCard video4 = VideoCard.builder()
                                .id("126")
                                .slug("hologram-in-pop-culture")
                                .title("Holograms in Pop Culture")
                                .thumbnail("https://images.unsplash.com/photo-1465101046530-73398c7f28ca")
                                .category("entertainment")
                                .tags(List.of(physicsTag, makerTag))
                                .likeCount(650)
                                .build();

                VideoCard video5 = VideoCard.builder()
                                .id("127")
                                .slug("future-of-holograms")
                                .title("The Future of Hologram Technology")
                                .thumbnail("https://images.unsplash.com/photo-1465101178521-c1a9136a3b99")
                                .category("technology")
                                .tags(List.of(physicsTag))
                                .likeCount(540)
                                .build();

                // Simulate a search with offset and limit
                List<VideoCard> videos = new ArrayList<>();
                videos.add(video1);
                videos.add(video2);
                videos.add(video3);
                videos.add(video4);
                videos.add(video5);

                return videos;
        }

}
