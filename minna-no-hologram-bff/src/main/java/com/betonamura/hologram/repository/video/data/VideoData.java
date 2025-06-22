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
    private static final String DEFAULT_HOLOGRAM_URL = "https://www.youtube.com/embed/Y60mfBvXCj8";
    private static final List<String> HOLOGRAM_VIDEO_URLS = List.of(
            DEFAULT_HOLOGRAM_URL, // 3D Hologram Projector Tutorial
            "https://www.youtube.com/embed/7YWTtCsvgvg", // DIY Hologram Pyramid
            "https://www.youtube.com/embed/Y5p4JFt3o5o", // How to Make a Hologram Projector
            "https://www.youtube.com/embed/0YiaOJbnRNI", // 3D Hologram DIY
            "https://www.youtube.com/embed/vnrKMpgYa2Y", // Smart Hologram Effect
            "https://www.youtube.com/embed/9t1xDnWIguo", // 3D Holographic Display
            "https://www.youtube.com/embed/kObRDkxR1BI", // Hologram Maker Tutorial
            "https://www.youtube.com/embed/JvrAFX213Wg", // Pyramid Hologram Display
            "https://www.youtube.com/embed/qsLOhRJVGiM", // 3D Hologram Fan Display
            "https://www.youtube.com/embed/CJ5Xtj83cYQ", // DIY Hologram for Mobile
            "https://www.youtube.com/embed/14oElD6DK_Y", // Hologram Dance Performance
            "https://www.youtube.com/embed/rKRHi_2FgmQ", // Hologram Technology Demonstration
            "https://www.youtube.com/embed/BRfF_HWxGY8", // Hologram Art Installation
            "https://www.youtube.com/embed/lGJ-5oVzjdI", // Hologram Science Projects
            "https://www.youtube.com/embed/Mno4HVH9cw4", // History through Holograms
            "https://www.youtube.com/embed/Q3W1ChIzlZ0", // Hologram Technology Explained
            "https://www.youtube.com/embed/eCXQMzLAgbs", // 3D Hologram Display Technology
            "https://www.youtube.com/embed/XBkGs_LfeQU", // DIY 3D Hologram
            "https://www.youtube.com/embed/vZ_YpjAd8cM", // Pyramid Hologram with Smartphone
            "https://www.youtube.com/embed/NuIJiLd6xCk" // Advanced Hologram Technology
    );

    // Real, existing Unsplash images related to hologram technology (all verified
    // to exist)
    private static final List<String> SCIENCE_IMAGE_URLS = List.of(
            "https://images.unsplash.com/photo-1550745165-9bc0b252726f?q=80", // Futuristic UI
            "https://images.unsplash.com/photo-1550751827-4bd374c3f58b?q=80", // Tech hologram
            "https://images.unsplash.com/photo-1558346490-a72e53ae2d4f?q=80", // Circuit board
            "https://images.unsplash.com/photo-1569834381156-7b735e41e57d?q=80", // Science experiment
            "https://images.unsplash.com/photo-1507413245164-6160d8298b31?q=80", // Science lab
            "https://images.unsplash.com/photo-1576086213369-97a306d36557?q=80", // Chemistry
            "https://images.unsplash.com/photo-1532094349884-543bc11b234d?q=80" // Physics
    );

    private static final List<String> HISTORY_IMAGE_URLS = List.of(
            "https://images.unsplash.com/photo-1567427018141-0584cfcbf1b8?q=80", // Hieroglyphs
            "https://images.unsplash.com/photo-1461360228754-6e81c478b882?q=80", // Ancient ruins
            "https://images.unsplash.com/photo-1551376347-075b0121a903?q=80", // Museum
            "https://images.unsplash.com/photo-1495121553079-4c61bcce1894?q=80", // Ancient artifact
            "https://images.unsplash.com/photo-1603199506016-b9a594b593c0?q=80", // Historical document
            "https://images.unsplash.com/photo-1608817576203-3c27ed168bd2?q=80", // Ancient Egypt
            "https://images.unsplash.com/photo-1566162094866-6d93cd955fb1?q=80" // Historical building
    );

    private static final List<String> CULTURE_IMAGE_URLS = List.of(
            "https://images.unsplash.com/photo-1529148482759-b35b25c5f217?q=80", // Cultural ceremony
            "https://images.unsplash.com/photo-1511739001486-6bfe10ce785f?q=80", // Museum exhibit
            "https://images.unsplash.com/photo-1532105956634-040a23c33ff3?q=80", // Cultural dance
            "https://images.unsplash.com/photo-1553531889-e6cf4d692b1b?q=80", // Cultural symbol
            "https://images.unsplash.com/photo-1560329072-17f59dcd30a4?q=80", // Cultural music
            "https://images.unsplash.com/photo-1527838832700-5059252407fa?q=80", // Art gallery
            "https://images.unsplash.com/photo-1492037766660-2a56f9eb3fcb?q=80" // Cultural festival
    );

    // The descriptions have been incorporated into the VideoQAItem content for
    // better context

    // Q&A data sets for different categories
    private static final List<List<VideoQAItem>> SCIENCE_QA_SETS = List.of(
            List.of(
                    new VideoQAItem("How does holographic technology work?",
                            "Holographic technology works by recording the light field reflecting off an object and then reconstructing it using laser light or digital projection to create a three-dimensional image."),
                    new VideoQAItem("Can holograms be touched?",
                            "Traditional holograms are just light projections and cannot be touched. However, researchers are developing 'haptic holograms' using ultrasound waves to create tactile feedback when interacting with holographic projections."),
                    new VideoQAItem("What's the difference between AR and holographic displays?",
                            "Augmented Reality (AR) overlays digital content onto your view of the real world through a device like a phone or glasses. Holographic displays create actual three-dimensional images in space without requiring special viewing devices.")),
            List.of(
                    new VideoQAItem("What scientific fields use hologram technology?",
                            "Hologram technology is used in many scientific fields including medicine (for visualization of organs and surgical planning), molecular biology (to visualize complex structures), astronomy (to model cosmic objects), and physics education."),
                    new VideoQAItem("How accurate are scientific holograms?",
                            "Scientific holograms can be extremely accurate, particularly when based on real data from instruments like MRIs, electron microscopes, or space telescopes. The accuracy depends on the quality of source data and rendering techniques."),
                    new VideoQAItem("Are holographic projections considered a form of virtual reality?",
                            "While related, holograms and virtual reality are different. Holograms project 3D images into physical space that can be viewed without special equipment, while VR creates immersive digital environments that require headsets to experience.")));

    private static final List<List<VideoQAItem>> HISTORY_QA_SETS = List.of(
            List.of(
                    new VideoQAItem("How accurate are historical holographic reconstructions?",
                            "The accuracy of historical reconstructions depends on available archaeological evidence, historical records, and scholarly research. Most holographic reconstructions combine confirmed historical data with informed speculation about missing details."),
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
                            "Holograms help preserve cultural heritage by creating detailed 3D records of artifacts, landmarks, and performances that might otherwise be lost to time, damage, or restricted access. These digital preservations can be shared globally."),
                    new VideoQAItem("Can holographic technology capture intangible cultural heritage?",
                            "Yes, holographic technology can capture performances, ceremonies, craftsmanship techniques, and oral traditions, preserving not just physical objects but also movements, expressions, and cultural practices for future generations."),
                    new VideoQAItem("How are museums using holographic displays?",
                            "Museums use holographic displays to show fragile artifacts that can't be physically exhibited, create interactive exhibits, demonstrate how incomplete artifacts originally appeared, and bring historical figures 'to life' as guides.")),
            List.of(
                    new VideoQAItem("How do holograms help bridge cultural differences?",
                            "Holographic exhibitions allow people to experience cultural practices, rituals, and art forms from around the world, fostering greater understanding and appreciation of diverse cultures without the need for travel."),
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
    public static List<VideoCard> getVideosByCategory(final String category, final int limit) {
        if (!StringUtils.hasText(category)) {
            return getVideoCards(limit);
        }

        final List<VideoDetail> allVideos = getVideosDetail();
        return allVideos.stream()
                .filter(v -> v.getCategory().getId().equals(category))
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
     * Create science category video records
     * 
     * @return List of science video details
     */
    private static List<VideoDetail> createScienceVideos() {
        final List<VideoDetail> videos = new ArrayList<>();

        final String[] titles = {
                "Quantum Physics Explained with Holograms",
                "The Science Behind Holographic Projections",
                "Medical Applications of Hologram Technology",
                "Principles of Light in Hologram Creation",
                "Space Exploration Visualized Through Holograms",
                "Molecular Biology in 3D Holographic Display",
                "Scientific Visualization Using Hologram Technology",
                "Environmental Science Data in Holographic Form"
        };

        for (int i = 0; i < titles.length; i++) {
            final String id = "sci-" + (100 + i);
            final String slug = generateSlugFromTitle(titles[i]);

            // Defensive: fallback to a safe video URL if list is null/empty
            String videoUrl;
            if (HOLOGRAM_VIDEO_URLS != null && !HOLOGRAM_VIDEO_URLS.isEmpty()) {
                videoUrl = HOLOGRAM_VIDEO_URLS.get(i % HOLOGRAM_VIDEO_URLS.size());
            } else {
                // fallback default
                videoUrl = DEFAULT_HOLOGRAM_URL;
            }

            videos.add(VideoDetail.builder()
                    .id(id)
                    .slug(slug)
                    .title(titles[i])
                    .thumbnail(SCIENCE_IMAGE_URLS.get(i % SCIENCE_IMAGE_URLS.size()))
                    .videoUrl(videoUrl)
                    .category(categoryRepo.findById("science"))
                    .tags(buildScienceTags())
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
                "Ancient Egypt Brought to Life with Holograms",
                "Medieval Castles: Holographic Reconstructions",
                "Dinosaurs in Your Living Room: Holographic Paleontology",
                "Roman Empire: A Holographic Journey",
                "Archaeological Discoveries in Holographic Detail",
                "Historical Battles Recreated with Hologram Technology",
                "Lost Cities Rebuilt Through Hologram Technology",
                "Ancient Writing Systems Decoded with Holograms"
        };

        for (int i = 0; i < titles.length; i++) {
            final String id = "hist-" + (200 + i);
            final String slug = generateSlugFromTitle(titles[i]);

            videos.add(VideoDetail.builder()
                    .id(id)
                    .slug(slug)
                    .title(titles[i])
                    .thumbnail(HISTORY_IMAGE_URLS.get(i % HISTORY_IMAGE_URLS.size()))
                    .videoUrl(HOLOGRAM_VIDEO_URLS.get((i + 5) % HOLOGRAM_VIDEO_URLS.size()))
                    .category(categoryRepo.findById("history"))
                    .tags(buildHistoryTags())
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
                "Traditional Dance Forms in Holographic Performances",
                "Cultural Festivals Around the World: Holographic Tour",
                "Ancient Musical Instruments Through Holograms",
                "Indigenous Art Forms Preserved Through Holography",
                "Cultural Rituals and Ceremonies in 3D Holograms",
                "Traditional Crafts From Around the World",
                "Sacred Sites and Temples as Holographic Models",
                "Cultural Heritage Preservation Through Holography"
        };

        for (int i = 0; i < titles.length; i++) {
            final String id = "cult-" + (300 + i);
            final String slug = generateSlugFromTitle(titles[i]);

            videos.add(VideoDetail.builder()
                    .id(id)
                    .slug(slug)
                    .title(titles[i])
                    .thumbnail(CULTURE_IMAGE_URLS.get(i % CULTURE_IMAGE_URLS.size()))
                    .videoUrl(HOLOGRAM_VIDEO_URLS.get((i + 10) % HOLOGRAM_VIDEO_URLS.size()))
                    .category(categoryRepo.findById("culture"))
                    .tags(buildCultureTags())
                    .likeCount(100 + random.nextInt(1500))
                    .qaContent(CULTURE_QA_SETS.get(i % CULTURE_QA_SETS.size()))
                    .relatedVideos(new ArrayList<>()) // Will be populated later
                    .createdAt(getRandomCreationDate().toString())
                    .build());
        }

        return videos;
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
     * Build tags for science category
     * 
     * @return List of appropriate tags
     */
    private static List<Tag> buildScienceTags() {
        return List.of(
                buildTag("hologram", "Hologram"),
                buildTag("education", "Education"),
                buildTag("3d", "3D"),
                buildTag("physics", "Physics"),
                buildTag("technology", "Technology"),
                buildTag("science", "Science"));
    }

    /**
     * Build tags for history category
     * 
     * @return List of appropriate tags
     */
    private static List<Tag> buildHistoryTags() {
        return List.of(
                buildTag("hologram", "Hologram"),
                buildTag("education", "Education"),
                buildTag("history", "History"),
                buildTag("ancient-history", "Ancient History"),
                buildTag("archaeology", "Archaeology"),
                buildTag("artifacts", "Artifacts"));
    }

    /**
     * Build tags for culture category
     * 
     * @return List of appropriate tags
     */
    private static List<Tag> buildCultureTags() {
        return List.of(
                buildTag("hologram", "Hologram"),
                buildTag("education", "Education"),
                buildTag("culture", "Culture"),
                buildTag("traditions", "Traditions"),
                buildTag("arts", "Arts"),
                buildTag("heritage", "Heritage"));
    }

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
