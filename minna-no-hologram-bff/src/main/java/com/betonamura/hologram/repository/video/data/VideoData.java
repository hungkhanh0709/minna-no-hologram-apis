package com.betonamura.hologram.repository.video.data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.betonamura.hologram.domain.tag.Tag;
import com.betonamura.hologram.domain.video.VideoCard;
import com.betonamura.hologram.domain.video.VideoDetail;
import com.betonamura.hologram.domain.video.VideoQAItem;
import com.betonamura.hologram.repository.category.CategoryRepository;

/**
 * Data provider for video content.
 * Contains a pre-populated set of detailed video records that can be used
 * for both detail views and search results.
 */
public class VideoData {

        private static final Random random = new Random(123); // Fixed seed for reproducibility
        private static final CategoryRepository categoryRepo = new CategoryRepository();

        // Real, verified hologram videos from YouTube (actual hologram tutorials and
        // demonstrations)
        private static final String DEFAULT_HOLOGRAM_URL = "https://www.youtube.com/embed/Y60mfBvXCj8"; // Hologram
                                                                                                        // Project
                                                                                                        // by Kiste
        private static final List<String> HOLOGRAM_VIDEO_URLS = List.of(
                        DEFAULT_HOLOGRAM_URL, // Hologram Project by Kiste
                        "https://www.youtube.com/embed/WYvYIAOP_S4", // All in One Hologram Technology Video For
                                                                     // Holographic Pyramid
                        "https://www.youtube.com/embed/9pan7fgDkxY", // HOLHO Collection: amazing 3D animals for 4 faces
                                                                     // Pyramid
                        "https://www.youtube.com/embed/ASX_d0H0HYw", // Plasma Dubstep - Pyramid Hologram Screen Up &
                                                                     // Down [4K]
                        "https://www.youtube.com/embed/Uftu0RaIDkw", // Hummingbird SloMo - Pyramid Hologram Screen Up
                                                                     // [4K]
                        "https://www.youtube.com/embed/BgUb7R9Tur0", // Jellyfish hologram video
                        "https://www.youtube.com/embed/Lz6YGP_yFaA", // Ironman hologram Technology Video
                        "https://www.youtube.com/embed/e5DOApUtf7M", // The Sun - Pyramid Hologram Screen Down
                        "https://www.youtube.com/embed/5DZ6D70m0pU", // The Moon - Pyramid Hologram Screen Up
                        "https://www.youtube.com/embed/z5lfdGhzF3Q", // The Moon - Pyramid Hologram Screen Down
                        "https://www.youtube.com/embed/DcDDNtuBZ5o", // Dragon Ball Z hologram Technology Video
                        "https://www.youtube.com/embed/tlO0qIk9N2o", // Dragon Ball Z - Pyramid Hologram - Holho
                        "https://www.youtube.com/embed/b2sWTWwh-oc", // Yin Yang Mutations - Pyramid Hologram Screen
                                                                     // Down
                        "https://www.youtube.com/embed/Xdj2Qoy1LhA", // Black Hole - Pyramid Hologram Screen Up & Down
                                                                     // [4K]
                        "https://www.youtube.com/embed/zzjqYbdSgxE", // Love Heart on hologram Technology Video
                        "https://www.youtube.com/embed/uW3I4OLqvjs", // BB-8 Star Wars Droid for Hologram Pyramid
                        "https://www.youtube.com/embed/R4AYYk0Nzf4", // Princess Leia Hologram - 4faces pyramid
                        "https://www.youtube.com/embed/asNoWcrfebk", // Blooming Flowers - Pyramid Hologram Screen Up
                        "https://www.youtube.com/embed/IGZ2bBII3GQ", // Spiderman Dance on hologram Technology Video
                        "https://www.youtube.com/embed/PuAb_GhO5Cc" // Ferrari Car Video For Holographic Pyramid
        );

        // Thumbnail URLs for the hologram videos from YouTube
        private static final List<String> HOLOGRAM_THUMBNAIL_URLS = List.of(
                        "https://img.youtube.com/vi/Y60mfBvXCj8/hqdefault.jpg", // Hologram Project by Kiste
                        "https://img.youtube.com/vi/WYvYIAOP_S4/hqdefault.jpg", // All in One Hologram Technology
                        "https://img.youtube.com/vi/9pan7fgDkxY/hqdefault.jpg", // HOLHO Collection: 3D animals
                        "https://img.youtube.com/vi/ASX_d0H0HYw/hqdefault.jpg", // Plasma Dubstep
                        "https://img.youtube.com/vi/Uftu0RaIDkw/hqdefault.jpg", // Hummingbird SloMo
                        "https://img.youtube.com/vi/BgUb7R9Tur0/hqdefault.jpg", // Jellyfish hologram
                        "https://img.youtube.com/vi/Lz6YGP_yFaA/hqdefault.jpg", // Ironman hologram
                        "https://img.youtube.com/vi/e5DOApUtf7M/hqdefault.jpg", // The Sun
                        "https://img.youtube.com/vi/5DZ6D70m0pU/hqdefault.jpg", // The Moon (Screen Up)
                        "https://img.youtube.com/vi/z5lfdGhzF3Q/hqdefault.jpg", // The Moon (Screen Down)
                        "https://img.youtube.com/vi/DcDDNtuBZ5o/hqdefault.jpg", // Dragon Ball Z hologram
                        "https://img.youtube.com/vi/tlO0qIk9N2o/hqdefault.jpg", // Dragon Ball Z - Holho
                        "https://img.youtube.com/vi/b2sWTWwh-oc/hqdefault.jpg", // Yin Yang Mutations
                        "https://img.youtube.com/vi/Xdj2Qoy1LhA/hqdefault.jpg", // Black Hole
                        "https://img.youtube.com/vi/zzjqYbdSgxE/hqdefault.jpg", // Love Heart
                        "https://img.youtube.com/vi/uW3I4OLqvjs/hqdefault.jpg", // BB-8 Star Wars
                        "https://img.youtube.com/vi/R4AYYk0Nzf4/hqdefault.jpg", // Princess Leia
                        "https://img.youtube.com/vi/asNoWcrfebk/hqdefault.jpg", // Blooming Flowers
                        "https://img.youtube.com/vi/IGZ2bBII3GQ/hqdefault.jpg", // Spiderman Dance
                        "https://img.youtube.com/vi/PuAb_GhO5Cc/hqdefault.jpg" // Ferrari Car Video
        );

        // Image URLs kept as fallback, though we're now primarily using YouTube
        // thumbnails
        private static final List<String> SCIENCE_IMAGE_URLS = List.of(
                        "https://images.unsplash.com/photo-1550745165-9bc0b252726f?q=80&w=1740", // Futuristic UI
                        "https://images.unsplash.com/photo-1550751827-4bd374c3f58b?q=80&w=1020", // Tech hologram
                        "https://images.unsplash.com/photo-1558346490-a72e53ae2d4f?q=80&w=1470", // Circuit board
                        "https://images.unsplash.com/photo-1569834381156-7b735e41e57d?q=80&w=1015", // Science
                                                                                                    // experiment
                        "https://images.unsplash.com/photo-1507413245164-6160d8298b31?q=80&w=1740", // Science lab
                        "https://images.unsplash.com/photo-1576086213369-97a306d36557?q=80&w=1480", // Chemistry
                        "https://images.unsplash.com/photo-1532094349884-543bc11b234d?q=80&w=1020" // Physics
        );

        // We now use the YouTube thumbnails directly, so these image collections are no
        // longer needed

        // The descriptions have been incorporated into the VideoQAItem content for
        // better context

        // Q&A data sets for different categories
        private static final List<List<VideoQAItem>> SCIENCE_QA_SETS = List.of(
                        List.of(
                                        new VideoQAItem("How does holographic technology work?",
                                                        "Holographic technology works by recording the light field reflecting off an object and then reconstructing it using laser light or digital projection to create a three-dimensional image."),
                                        new VideoQAItem("Can holograms be touched?",
                                                        "Traditional holograms are just light projections and cannot be touched. However, researchers are developing 'haptic holograms' using ultrasound waves to create tactile feedback when interacting with holographic projections.",
                                                        "https://images.unsplash.com/photo-1550751827-4bd374c3f58b?q=80&w=1020"),
                                        new VideoQAItem("What's the difference between AR and holographic displays?",
                                                        "Augmented Reality (AR) overlays digital content onto your view of the real world through a device like a phone or glasses. Holographic displays create actual three-dimensional images in space without requiring special viewing devices.")),
                        List.of(
                                        new VideoQAItem("What scientific fields use hologram technology?",
                                                        "Hologram technology is used in many scientific fields including medicine (for visualization of organs and surgical planning), molecular biology (to visualize complex structures), astronomy (to model cosmic objects), and physics education."),
                                        new VideoQAItem("How accurate are scientific holograms?",
                                                        "Scientific holograms can be extremely accurate, particularly when based on real data from instruments like MRIs, electron microscopes, or space telescopes. The accuracy depends on the quality of source data and rendering techniques."),
                                        new VideoQAItem("Are holographic projections considered a form of virtual reality?",
                                                        "While related, holograms and virtual reality are different. Holograms project 3D images into physical space that can be viewed without special equipment, while VR creates immersive digital environments that require headsets to experience.",
                                                        "https://images.unsplash.com/photo-1622979135225-d2ba269cf1ac?q=80&w=987")));

        private static final List<List<VideoQAItem>> HISTORY_QA_SETS = List.of(
                        List.of(
                                        new VideoQAItem("How accurate are historical holographic reconstructions?",
                                                        "The accuracy of historical reconstructions depends on available archaeological evidence, historical records, and scholarly research. Most holographic reconstructions combine confirmed historical data with informed speculation about missing details.",
                                                        "https://images.unsplash.com/photo-1531771686035-25f47595c87a?q=80&w=1025"),
                                        new VideoQAItem("How do archaeologists use hologram technology?",
                                                        "Archaeologists use hologram technology to create non-invasive 3D models of fragile artifacts, virtually reconstruct damaged or incomplete findings, and visualize entire archaeological sites as they might have appeared in their original state."),
                                        new VideoQAItem("Can holographic technology reveal hidden details in ancient artifacts?",
                                                        "Yes, holographic imaging can reveal details not visible to the naked eye, such as worn inscriptions, layered materials, or internal structures, without physically handling or potentially damaging delicate historical items.")),
                        List.of(
                                        new VideoQAItem("How do historians ensure holographic recreations are historically accurate?",
                                                        "Historians work with multidisciplinary teams including archaeologists, anthropologists, and historical records experts to analyze primary sources, archaeological evidence, and contextual information before creating holographic recreations."),
                                        new VideoQAItem("What's the educational benefit of historical holograms?",
                                                        "Historical holograms provide immersive, interactive learning experiences that can make history more engaging and memorable. They allow people to 'see' the past in ways traditional books or even videos cannot match."),
                                        new VideoQAItem("How are historical holograms different from historical reenactments?",
                                                        "Historical reenactments involve people physically recreating historical events, while holographic recreations use technology to visualize history. Holograms can show things impossible to reenact, like ancient cities or historical figures from multiple eras.")));

        private static final List<List<VideoQAItem>> CULTURE_QA_SETS = List.of(
                        List.of(
                                        new VideoQAItem("How are holograms helping preserve cultural heritage?",
                                                        "Holograms help preserve cultural heritage by creating detailed 3D records of artifacts, landmarks, and performances that might otherwise be lost to time, damage, or restricted access. These digital preservations can be shared globally.",
                                                        "https://images.unsplash.com/photo-1569834381156-7b735e41e57d?q=80&w=1015"),
                                        new VideoQAItem("Can holographic technology capture intangible cultural heritage?",
                                                        "Yes, holographic technology can capture performances, ceremonies, craftsmanship techniques, and oral traditions, preserving not just physical objects but also movements, expressions, and cultural practices for future generations."),
                                        new VideoQAItem("How are museums using holographic displays?",
                                                        "Museums use holographic displays to show fragile artifacts that can't be physically exhibited, create interactive exhibits, demonstrate how incomplete artifacts originally appeared, and bring historical figures 'to life' as guides.")),
                        List.of(
                                        new VideoQAItem("How do holograms help bridge cultural differences?",
                                                        "Holographic exhibitions allow people to experience cultural practices, rituals, and art forms from around the world, fostering greater understanding and appreciation of diverse cultures without the need for travel.",
                                                        "https://images.unsplash.com/photo-1614624532983-4ce03382d63d?q=80&w=1031"),
                                        new VideoQAItem("Are traditional craftspeople involved in creating cultural holograms?",
                                                        "Yes, many cultural hologram projects collaborate directly with traditional craftspeople, performers, and cultural knowledge keepers to ensure authentic representation and preserve their specialized knowledge."),
                                        new VideoQAItem("Can holographic technology help revitalize endangered cultural practices?",
                                                        "Holographic technology can help revitalize endangered cultural practices by creating detailed records that new practitioners can learn from, and by making these cultural practices more accessible and engaging to younger generations.")));

        /**
         * Create detailed records for all videos
         * 
         * @return List of detailed video records
         */
        public static List<VideoDetail> getVideosDetail() {
                List<VideoDetail> videos = new ArrayList<>();

                // Add science videos
                videos.addAll(createScienceVideos());

                // Add history videos
                videos.addAll(createHistoryVideos());

                // Add culture videos
                videos.addAll(createCultureVideos());

                return videos;
        }

        /**
         * Get a video detail by its slug
         * 
         * @param slug The slug identifier of the video
         * @return The video detail if found, otherwise null
         */
        public static VideoDetail getVideoDetailBySlug(final String slug) {
                if (!StringUtils.hasText(slug)) {
                        return null;
                }

                // Loop through all videos to find a match
                final List<VideoDetail> allVideos = getVideosDetail();
                return allVideos.stream()
                                .filter(video -> video.getSlug().equalsIgnoreCase(slug))
                                .findFirst()
                                .orElse(null);
        }

        /**
         * Get a list of all video data in VideoCard format
         * This is used for search operations
         * 
         * @param limit Maximum number of videos to return
         * @return List of VideoCard objects
         */
        public static List<VideoCard> getVideoCards(final int limit) {
                final List<VideoDetail> allVideos = getVideosDetail();
                return allVideos.stream()
                                .limit(Math.min(limit, allVideos.size())) // Limit to the requested number or total size
                                .map(VideoData::toVideoCard)
                                .collect(Collectors.toList());
        }

        /**
         * Get videos filtered by category
         *
         * @param category Category to filter by
         * @param limit    Maximum number of videos to return
         * @return Filtered list of VideoCard objects
         */
        public static List<VideoCard> getVideosByCategory(final String videoId, final String category,
                        final int limit) {
                if (!StringUtils.hasText(category)) {
                        return getVideoCards(limit);
                }

                final List<VideoDetail> allVideos = getVideosDetail();
                return allVideos.stream()
                                .filter(v -> v.getCategory().getId().equals(category))
                                .filter(v -> videoId == null || !v.getId().equals(videoId))
                                .limit(limit)
                                .map(VideoData::toVideoCard)
                                .collect(Collectors.toList());
        }

        /**
         * Convert a VideoDetail to a VideoCard
         *
         * @param detail The detailed video record
         * @return VideoCard representation
         */
        public static VideoCard toVideoCard(final VideoDetail detail) {
                return VideoCard.builder()
                                .id(detail.getId())
                                .slug(detail.getSlug())
                                .title(detail.getTitle())
                                .thumbnail(detail.getThumbnail())
                                .category(categoryRepo.findById(detail.getCategory().getId()))
                                .tags(detail.getTags())
                                .likeCount(detail.getLikeCount())
                                .build();
        }

        /**
         * Generate a slug from a title
         * 
         * @param title The title to convert
         * @return URL-friendly slug
         */
        private static String generateSlugFromTitle(String title) {
                return title.toLowerCase()
                                .replaceAll("[^a-z0-9\\s-]", "")
                                .replaceAll("\\s+", "-")
                                .replaceAll("-+", "-");
        }

        /**
         * Get a random creation date for content
         * 
         * @return Random date within the last 3 years
         */
        private static OffsetDateTime getRandomCreationDate() {
                int year = 2022 + random.nextInt(4); // 2022-2025
                int month = 1 + random.nextInt(12); // 1-12
                int day = 1 + random.nextInt(28); // 1-28
                int hour = random.nextInt(24);
                int minute = random.nextInt(60);

                return OffsetDateTime.parse(
                                String.format("%04d-%02d-%02dT%02d:%02d:00Z", year, month, day, hour, minute));
        }

        /**
         * Build dynamic tags for a video based on its title and category
         * 
         * @param title    The title of the video
         * @param index    The index of the video in the list
         * @param category The category of the video
         * @return List of dynamically generated tags
         */
        private static List<Tag> generateDynamicTags(String title, int index, String category) {
                List<Tag> tags = new ArrayList<>();
                tags.add(buildTag("hologram", "Hologram"));
                tags.add(buildTag(category, StringUtils.capitalize(category)));
                if (title.toLowerCase().contains("physics"))
                        tags.add(buildTag("physics", "Physics"));
                if (title.toLowerCase().contains("biology"))
                        tags.add(buildTag("biology", "Biology"));
                if (title.toLowerCase().contains("history"))
                        tags.add(buildTag("history", "History"));
                if (title.toLowerCase().contains("art"))
                        tags.add(buildTag("arts", "Arts"));
                if (title.toLowerCase().contains("music"))
                        tags.add(buildTag("music", "Music"));
                if (index % 2 == 0)
                        tags.add(buildTag("education", "Education"));
                if (index % 3 == 0)
                        tags.add(buildTag("technology", "Technology"));
                return tags;
        }

        /**
         * Create science category video records
         * 
         * @return List of science video details
         */
        private static List<VideoDetail> createScienceVideos() {
                final List<VideoDetail> videos = new ArrayList<>();

                final String[] titles = {
                                "Hologram Technology: Principles and Applications", // Hologram Project by Kiste
                                "All in One Hologram Technology for Holographic Pyramid",
                                "3D Animals for 4 Faces Pyramid Hologram",
                                "Plasma Dubstep - Pyramid Hologram [4K]",
                                "Hummingbird SloMo - Advanced Holographic Display",
                                "Jellyfish Hologram: Underwater Life in 3D",
                                "Molecular Visualization Using Holographic Technology",
                                "Environmental Science Data in Holographic Form"
                };

                for (int i = 0; i < titles.length; i++) {
                        final String id = "sci-" + (100 + i);
                        final String slug = generateSlugFromTitle(titles[i]);

                        // Defensive: fallback to a safe video URL if list is null/empty
                        String videoUrl;
                        String thumbnail;
                        if (HOLOGRAM_VIDEO_URLS != null && !HOLOGRAM_VIDEO_URLS.isEmpty()) {
                                videoUrl = HOLOGRAM_VIDEO_URLS.get(i % HOLOGRAM_VIDEO_URLS.size());
                                thumbnail = HOLOGRAM_THUMBNAIL_URLS.get(i % HOLOGRAM_THUMBNAIL_URLS.size());
                        } else {
                                // fallback default
                                videoUrl = DEFAULT_HOLOGRAM_URL;
                                thumbnail = SCIENCE_IMAGE_URLS.get(i % SCIENCE_IMAGE_URLS.size());
                        }

                        videos.add(VideoDetail.builder()
                                        .id(id)
                                        .slug(slug)
                                        .title(titles[i])
                                        .thumbnail(thumbnail)
                                        .videoUrl(videoUrl)
                                        .category(categoryRepo.findById("science"))
                                        .tags(generateDynamicTags(titles[i], i, "science"))
                                        .likeCount(100 + random.nextInt(1500))
                                        .qaContent(SCIENCE_QA_SETS.get(i % SCIENCE_QA_SETS.size()))
                                        .relatedVideos(new ArrayList<>()) // Will be populated later
                                        .createdAt(getRandomCreationDate().toString())
                                        .build());
                }

                return videos;
        }

        /**
         * Create history category video records
         * 
         * @return List of history video details
         */
        private static List<VideoDetail> createHistoryVideos() {
                final List<VideoDetail> videos = new ArrayList<>();

                final String[] titles = {
                                "The Sun - Pyramid Hologram Screen Down",
                                "The Moon - Pyramid Hologram Screen Up",
                                "The Moon - Pyramid Hologram Screen Down",
                                "Yin Yang Mutations - Pyramid Hologram Visualization",
                                "Black Hole - Pyramid Hologram Screen [4K]",
                                "BB-8 Star Wars Droid - 4 Side Pyramid Hologram",
                                "Princess Leia Hologram - Star Wars 4-Face Pyramid",
                                "Blooming Flowers - Historical Hologram Recreation"
                };

                for (int i = 0; i < titles.length; i++) {
                        final String id = "hist-" + (200 + i);
                        final String slug = generateSlugFromTitle(titles[i]);

                        // Select video URL and thumbnail with appropriate offset
                        final int videoIndex = (i + 7) % HOLOGRAM_VIDEO_URLS.size(); // Start from 7th video
                        final String videoUrl = HOLOGRAM_VIDEO_URLS.get(videoIndex);
                        final String thumbnail = HOLOGRAM_THUMBNAIL_URLS.get(videoIndex);

                        videos.add(VideoDetail.builder()
                                        .id(id)
                                        .slug(slug)
                                        .title(titles[i])
                                        .thumbnail(thumbnail)
                                        .videoUrl(videoUrl)
                                        .category(categoryRepo.findById("history"))
                                        .tags(generateDynamicTags(titles[i], i, "history"))
                                        .likeCount(100 + random.nextInt(1500))
                                        .qaContent(HISTORY_QA_SETS.get(i % HISTORY_QA_SETS.size()))
                                        .relatedVideos(new ArrayList<>()) // Will be populated later
                                        .createdAt(getRandomCreationDate().toString())
                                        .build());
                }

                return videos;
        }

        /**
         * Create culture category video records
         * 
         * @return List of culture video details
         */
        private static List<VideoDetail> createCultureVideos() {
                final List<VideoDetail> videos = new ArrayList<>();

                final String[] titles = {
                                "Love Heart - Holographic Pyramid 4-Face View",
                                "Dragon Ball Z - Hologram 4-Face View",
                                "Dragon Ball Z - Pyramid Hologram - Holho",
                                "Ironman Hologram Technology Video",
                                "Ferrari Car Video For Holographic Pyramid",
                                "Spiderman Dance - Holographic Pyramid",
                                "Hologram Video Collection - 3D Display",
                                "Minion Dance - Holographic Pyramid Display"
                };

                for (int i = 0; i < titles.length; i++) {
                        final String id = "cult-" + (300 + i);
                        final String slug = generateSlugFromTitle(titles[i]);

                        // Select video URL and thumbnail with appropriate offset
                        final int videoIndex = (i + 11) % HOLOGRAM_VIDEO_URLS.size(); // Start from 11th video
                        final String videoUrl = HOLOGRAM_VIDEO_URLS.get(videoIndex);
                        final String thumbnail = HOLOGRAM_THUMBNAIL_URLS.get(videoIndex);

                        videos.add(VideoDetail.builder()
                                        .id(id)
                                        .slug(slug)
                                        .title(titles[i])
                                        .thumbnail(thumbnail)
                                        .videoUrl(videoUrl)
                                        .category(categoryRepo.findById("culture"))
                                        .tags(generateDynamicTags(titles[i], i, "culture"))
                                        .likeCount(100 + random.nextInt(1500))
                                        .qaContent(CULTURE_QA_SETS.get(i % CULTURE_QA_SETS.size()))
                                        .relatedVideos(new ArrayList<>()) // Will be populated later
                                        .createdAt(getRandomCreationDate().toString())
                                        .build());
                }

                return videos;
        }

        // These tag builder methods have been replaced by the more dynamic
        // generateDynamicTags method

        /**
         * Build a tag object
         * 
         * @param id   Tag identifier
         * @param name Tag display name
         * @return Tag object
         */
        private static Tag buildTag(String id, String name) {
                return Tag.builder()
                                .id(id)
                                .name(name)
                                .build();
        }
}
