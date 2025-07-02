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

                // // Add science videos
                // videos.addAll(createScienceVideos());

                // // Add history videos
                // videos.addAll(createHistoryVideos());

                // // Add culture videos
                // videos.addAll(createCultureVideos());

                videos.addAll(createVideos()); // Add science videos first
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
                                .summary(detail.getSummary())
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

        private static List<VideoDetail> createVideos() {
                final List<VideoDetail> videos = new ArrayList<>();

                // Jellyfish hologram video
                int i = 1;
                String title = "Jellyfish";
                videos.add(VideoDetail.builder()
                                .id(String.valueOf(videos.size() + i))
                                .slug(generateSlugFromTitle(title))
                                .title(title)
                                .thumbnail("https://www.nhm.ac.uk/content/dam/nhm-www/discover/jellyfish/moon-jellyfish-aurelia-aurita-floating-in-the-ocean-two-column.jpg.thumb.768.768.png")
                                .summary("Dive into the ethereal world of jellyfish with this mesmerizing holographic experience. Watch as these graceful marine creatures float and pulse through 3D space, showcasing their translucent bells, flowing tentacles, and bioluminescent beauty. This hologram captures the hypnotic dance of jellyfish in their natural aquatic environment, making you feel like you're swimming alongside these ancient ocean dwellers.")
                                .videoUrl("https://www.youtube.com/embed/BgUb7R9Tur0?si=XUsGmM2mFdHDGyw_")
                                .category(categoryRepo.findById("science"))
                                .tags(List.of(
                                                buildTag("fish", "Fish"),
                                                buildTag("biology", "Biology")))
                                .likeCount(random.nextInt(1500))
                                .qaContent(List.of(
                                                new VideoQAItem("Master, what makes jellyfish glow in the hologram?",
                                                                "Ah, excellent question! Jellyfish produce light through bioluminescence - a chemical reaction in their bodies that creates light without heat. In our hologram, we've enhanced this natural glow to show how they use this light to communicate, defend themselves, or attract prey in the deep ocean."),
                                                new VideoQAItem("Why do jellyfish move so gracefully through the water?",
                                                                "Jellyfish are masterfully designed by nature! They contract their bell-shaped body to push water out, which propels them forward. Their graceful movement is actually very efficient - they use 48% less energy than other swimming animals. Our hologram captures this fluid motion in three dimensions.",
                                                                "https://images.twinkl.co.uk/tw1n/image/private/t_630/u/ux/jellyfish_ver_1.jpg"),
                                                new VideoQAItem("Are jellyfish really fish, Master?",
                                                                "Great observation! Despite their name, jellyfish are not fish at all. They're cnidarians - simple animals without brains, hearts, or blood. They've existed for over 500 million years, making them older than dinosaurs! Our hologram shows their simple but effective body structure."),
                                                new VideoQAItem("How do jellyfish eat without a mouth?",
                                                                "Fascinating question! Jellyfish have a single opening that serves as both mouth and anus. They capture prey with their stinging tentacles, then push the food into their gastrovascular cavity. Watch carefully in the hologram - you can see how their tentacles move to catch microscopic prey.",
                                                                "https://s3.eu-west-1.amazonaws.com/media.mcsuk.org/images/RS31885_image-131-lpr.width-765.jpg"),
                                                new VideoQAItem("Can jellyfish really live forever?",
                                                                "Some can! The immortal jellyfish, Turritopsis dohrnii, can reverse its aging process and return to its juvenile state. While most jellyfish live weeks to months, this species has achieved biological immortality. Our hologram demonstrates the life cycle that makes this possible.")))
                                .relatedVideos(new ArrayList<>()) // Will be populated later
                                .createdAt(getRandomCreationDate().toString())
                                .build());

                // Hummingbird
                i++;
                title = "Hummingbird";
                videos.add(VideoDetail.builder()
                                .id(String.valueOf(videos.size() + i))
                                .slug(generateSlugFromTitle(title))
                                .title(title)
                                .thumbnail("https://riosalado.audubon.org/sites/default/files/styles/bean_wysiwyg_full_width/public/elaine_r._wilson_hummingbird.jpg")
                                .summary("Experience the marvel of hummingbird flight in breathtaking detail through this holographic showcase. Watch these tiny aviators hover, dart, and dance with wings beating up to 80 times per second. This 3D hologram reveals the intricate mechanics of their unique flight patterns, iridescent plumage, and incredible agility that makes them nature's most skilled aerial acrobats.")
                                .videoUrl("https://www.youtube.com/embed/Uftu0RaIDkw?si=8X4-1eLjUQYhW3ID")
                                .category(categoryRepo.findById("science"))
                                .tags(List.of(
                                                buildTag("bird", "Bird"),
                                                buildTag("biology", "Biology")))
                                .likeCount(random.nextInt(1500))
                                .qaContent(List.of(
                                                new VideoQAItem("Master, how can hummingbirds fly backwards?",
                                                                "Remarkable question! Hummingbirds have a unique wing structure that allows them to rotate their wings in a figure-8 pattern. Unlike other birds that generate lift only on the downstroke, hummingbirds create lift on both down and upstrokes, enabling them to hover, fly backwards, and even upside down!",
                                                                "https://www.allaboutbirds.org/guide/assets/photo/303878421-480px.jpg"),
                                                new VideoQAItem("Why do hummingbirds beat their wings so fast?",
                                                                "Their rapid wingbeats - up to 80 beats per second - are necessary to support their hovering flight and quick maneuvers. Their wings are proportionally smaller than other birds, so they must beat faster to generate enough lift. Our hologram shows this incredible speed in slow motion so you can see each wing movement."),
                                                new VideoQAItem("How do hummingbirds change colors in the hologram?",
                                                                "Excellent observation! Hummingbirds have iridescent feathers with microscopic structures that reflect light differently at various angles. As they move in our 3D hologram, you can see how their colors shift from emerald to ruby to gold, just like they do in nature when the light hits them differently."),
                                                new VideoQAItem("Do hummingbirds really migrate such long distances?",
                                                                "Absolutely! Despite weighing less than a nickel, some hummingbirds migrate over 3,000 miles. The Ruby-throated Hummingbird flies 500 miles non-stop across the Gulf of Mexico! Their tiny bodies are incredibly efficient, storing fat that doubles their weight before migration."),
                                                new VideoQAItem("Master, how much nectar do hummingbirds need each day?",
                                                                "These tiny powerhouses visit 1,000-2,000 flowers daily and consume half their body weight in nectar! They also eat small insects for protein. Their metabolism is so fast that they must eat every 10-15 minutes during daylight hours. Watch closely in the hologram - you might see their feeding behavior!",
                                                                "https://www.housedigest.com/img/gallery/7-types-of-beautiful-hummingbirds-you-might-find-in-your-backyard/intro-1716748789.jpg")))
                                .relatedVideos(new ArrayList<>()) // Will be populated later
                                .createdAt(getRandomCreationDate().toString())
                                .build());

                // Blooming Flowers
                i++;
                title = "Blooming Flowers";
                videos.add(VideoDetail.builder()
                                .id(String.valueOf(videos.size() + i))
                                .slug(generateSlugFromTitle(title))
                                .title(title)
                                .thumbnail("https://images.immediate.co.uk/production/volatile/sites/10/2024/03/2048x1365-sunflowers-SEO-LI1097451DSF4261-f1aa805.jpg?quality=90&fit=700,466")
                                .summary("Witness nature's most beautiful transformation through this stunning holographic display of blooming flowers. Watch as buds slowly unfurl their petals, revealing vibrant colors and intricate patterns in mesmerizing detail. This 3D hologram captures the delicate dance of petals as they open, showcasing the miracle of botanical life in a breathtaking visual symphony.")
                                .videoUrl("https://www.youtube.com/embed/asNoWcrfebk?si=ayq3GUA4jrWwR-EN")
                                .category(categoryRepo.findById("science"))
                                .tags(List.of(
                                                buildTag("hologram", "Hologram"),
                                                buildTag("nature", "Nature")))
                                .likeCount(random.nextInt(1500))
                                .qaContent(List.of(
                                                new VideoQAItem("Master, why do flowers bloom at different times?",
                                                                "Great question! Flowers have evolved to bloom at specific times to maximize their chances of reproduction. Some bloom early to avoid competition, others wait for their preferred pollinators to be active. Our hologram shows how each flower has its own perfect timing, synchronized with seasons, temperature, and daylight hours."),
                                                new VideoQAItem("How do flowers know when to open their petals?",
                                                                "Flowers are incredibly smart! They respond to environmental cues like light, temperature, and humidity. Many flowers have internal biological clocks that tell them the optimal time to open. In our hologram, you can see how the petals respond to these invisible signals, opening in perfect harmony with nature."),
                                                new VideoQAItem("Why are flowers so colorful in the hologram?",
                                                                "Excellent observation! Flower colors are nature's advertising - they're designed to attract specific pollinators. Red flowers attract birds, blue and purple attract bees, and white flowers often attract night pollinators like moths. Our 3D hologram enhances these natural colors to show how flowers communicate through their beauty.",
                                                                "https://cdn.shopify.com/s/files/1/1740/1449/files/RG-Sunflower-1.jpg?v=1631119406"),
                                                new VideoQAItem("Master, do all flowers bloom the same way?",
                                                                "Not at all! Each flower species has its own unique blooming pattern. Some unfurl slowly like roses, others pop open quickly like poppies, and some bloom in stages like lilies. Our hologram captures these different blooming styles, showing the incredible diversity in how nature creates beauty."),
                                                new VideoQAItem("Can flowers really predict the weather?",
                                                                "Indeed they can! Many flowers close their petals before rain or when humidity rises, protecting their pollen and nectar. Some flowers track the sun's movement throughout the day. In our hologram, you can observe these natural behaviors that have helped flowers survive for millions of years.",
                                                                "https://homegardenandhomestead.com/wp-content/uploads/2022/08/sunflowers-sunglasses.jpeg")))
                                .relatedVideos(new ArrayList<>()) // Will be populated later
                                .createdAt(getRandomCreationDate().toString())
                                .build());

                // Butterfly
                i++;
                title = "Butterfly";
                videos.add(VideoDetail.builder()
                                .id(String.valueOf(videos.size() + i))
                                .slug(generateSlugFromTitle(title))
                                .title(title)
                                .thumbnail("https://www.insectlore.com/cdn/shop/files/Pinkflatlaycopy_1200x.png?v=1724880310")
                                .summary("Experience the magical metamorphosis and graceful flight of butterflies in this enchanting holographic display. Watch as these delicate creatures flutter through 3D space, showcasing their intricate wing patterns, vibrant colors, and ethereal beauty. This hologram captures the essence of transformation and the delicate elegance of one of nature's most beloved insects.")
                                .videoUrl("https://www.youtube.com/embed/mjgq6koC6J0?si=sgVyRIrVrVP0ZN5D")
                                .category(categoryRepo.findById("science"))
                                .tags(List.of(
                                                buildTag("biology", "Biology"),
                                                buildTag("butterfly", "Butterfly")))
                                .likeCount(random.nextInt(1500))
                                .qaContent(List.of(
                                                new VideoQAItem("Master, how do butterflies get their beautiful colors?",
                                                                "Wonderful question! Butterfly wings have tiny scales that contain pigments and microscopic structures that reflect light. Some colors come from pigments like melanin, while others are created by light interference - like how soap bubbles shimmer. Our hologram shows how these colors change as the butterfly moves through different angles of light.",
                                                                "https://imgc.artprintimages.com/img/print/suns-luck-color-butterfly-flying_u-l-pofq920.jpg?artHeight=550&artPerspective=n&artWidth=550&background=fbfbfb"),
                                                new VideoQAItem("Why do butterflies flutter instead of flying straight?",
                                                                "Great observation! Butterflies' erratic flight pattern is actually a survival strategy - it makes them harder for predators to catch. Their large wings relative to their body weight also make them more susceptible to air currents. In our 3D hologram, you can see how each wing beat creates beautiful, unpredictable movements."),
                                                new VideoQAItem("How does a caterpillar turn into a butterfly in the hologram?",
                                                                "That's one of nature's greatest mysteries! Inside the chrysalis, the caterpillar's body completely dissolves except for special groups of cells called imaginal discs, which rebuild into wings, legs, and other butterfly parts. Our hologram demonstrates this incredible transformation process that takes about 2 weeks."),
                                                new VideoQAItem("Master, why do some butterflies migrate thousands of miles?",
                                                                "Remarkable, isn't it? Monarch butterflies migrate up to 3,000 miles using the sun as a compass and possibly magnetic fields for navigation. What's amazing is that the butterflies making the return journey have never been there before - they inherit this knowledge genetically! Our hologram shows their graceful flight patterns.",
                                                                "https://eowilsonfoundation.org/app/uploads/2025/04/shutterstock_2472168933-1024x683.jpg"),
                                                new VideoQAItem("Do butterflies really taste with their feet?",
                                                                "Yes indeed! Butterflies have chemoreceptors on their feet that help them taste potential food sources and identify the right plants for laying eggs. When you see a butterfly land on a flower in our hologram, it's actually 'tasting' to see if it's suitable. They can detect sweetness levels that way!")))
                                .relatedVideos(new ArrayList<>()) // Will be populated later
                                .createdAt(getRandomCreationDate().toString())
                                .build());

                // Clown Fish
                i++;
                title = "Clown Fish";
                videos.add(VideoDetail.builder()
                                .id(String.valueOf(videos.size() + i))
                                .slug(generateSlugFromTitle(title))
                                .title(title)
                                .thumbnail("https://cdn.mos.cms.futurecdn.net/4UdEs7tTKwLJbxZPUYR3hF-1200-80.jpg")
                                .summary("Dive into the vibrant underwater world of clownfish through this captivating holographic experience. Watch these iconic orange-and-white striped fish as they playfully swim around their sea anemone homes. This 3D hologram showcases their symbiotic relationship with anemones, their territorial behaviors, and the colorful coral reef ecosystem they call home.")
                                .videoUrl("https://www.youtube.com/embed/9pan7fgDkxY?si=6b0d1a2f8c3e4c5b")
                                .category(categoryRepo.findById("science"))
                                .tags(List.of(
                                                buildTag("hologram", "Hologram"),
                                                buildTag("biology", "Biology"),
                                                buildTag("fish", "Fish")))
                                .likeCount(random.nextInt(1500))
                                .qaContent(List.of(
                                                new VideoQAItem("Master, why don't sea anemones sting clownfish?",
                                                                "Excellent question! Clownfish have a special protective mucus coating that makes them immune to anemone stings. They gradually build up this immunity by carefully touching the anemone tentacles. It's like having a natural shield! In our hologram, you can see how the clownfish swim fearlessly through the anemone's tentacles.",
                                                                "https://www.algaebarn.com/wp-content/uploads/2020/10/shutterstock_391864198-scaled.jpg"),
                                                new VideoQAItem("Why are clownfish orange and white striped?",
                                                                "Great observation! Their bright colors serve multiple purposes - the orange makes them visible to their anemone partners, while the white stripes help them recognize their own species. The bold pattern also confuses predators when they dart in and out of anemone tentacles. Our 3D hologram shows how these colors pop in the coral reef environment."),
                                                new VideoQAItem("How do clownfish help sea anemones?",
                                                                "It's a beautiful partnership! Clownfish bring food scraps to the anemone, remove parasites, and their movements help circulate water around the anemone for better breathing. They also defend their anemone home from other fish. Watch in our hologram how they constantly tend to their anemone partner - it's like underwater gardening!"),
                                                new VideoQAItem("Master, do all clownfish live in groups?",
                                                                "Yes, and here's something fascinating - clownfish live in hierarchical groups led by a dominant female! If the female dies, the largest male will actually change into a female to take her place. They're sequential hermaphrodites. Our hologram shows how they interact in their family groups within their anemone territory."),
                                                new VideoQAItem("Can clownfish survive without anemones?",
                                                                "While they can survive briefly without anemones, they're much more vulnerable to predators and stress. The anemone provides protection, shelter for eggs, and a territory to defend. In our hologram, you can see how the anemone serves as their safe haven - they rarely venture far from home!",
                                                                "https://cdn.britannica.com/80/160480-004-10A06884/clown-fish-appearance-anemone.jpg")))
                                .relatedVideos(new ArrayList<>()) // Will be populated later
                                .createdAt(getRandomCreationDate().toString())
                                .build());

                // Graviton, Proton, Neutron, Elektron Particles
                i++;
                title = "Graviton, Proton, Neutron, Elektron Particles";
                videos.add(VideoDetail.builder()
                                .id(String.valueOf(videos.size() + i))
                                .slug(generateSlugFromTitle(title))
                                .title(title)
                                .thumbnail("https://cdn1-production-images-kly.akamaized.net/aAjmtMr5Pu6Es1239gGQ3YYyWHI=/800x450/smart/filters:quality(75):strip_icc():format(webp)/kly-media-production/medias/4620827/original/038926500_1698052322-waves-7854066_1280.jpg")
                                .summary("Journey into the quantum realm with this fascinating holographic visualization of fundamental particles. Witness gravitons, protons, neutrons, and electrons as they interact in the microscopic world that forms the foundation of all matter. This 3D hologram brings abstract physics concepts to life, showing particle behaviors, interactions, and the forces that govern our universe at its most basic level.")
                                .videoUrl("https://www.youtube.com/embed/aMmlS29Dz6Q?si=Z_dxopspn8ZaK7Fi")
                                .category(categoryRepo.findById("science"))
                                .tags(List.of(
                                                buildTag("physics", "Physics"),
                                                buildTag("particles", "Particles")))
                                .likeCount(random.nextInt(1500))
                                .qaContent(List.of(
                                                new VideoQAItem("Master, what exactly are these fundamental particles?",
                                                                "Excellent question! These are the building blocks of everything in our universe. Protons and neutrons form atomic nuclei, electrons orbit around them, and gravitons are theoretical particles that carry gravitational force. Think of them as nature's LEGO blocks - everything you see is made from these tiny components!"),
                                                new VideoQAItem("Why can't we see these particles with our eyes?",
                                                                "Great observation! These particles are incredibly tiny - millions of times smaller than anything we can see. Even the most powerful microscopes can't show them directly. Our hologram uses scientific data and quantum mechanics to visualize how they would behave if we could see them. It's like making the invisible visible!"),
                                                new VideoQAItem("How do electrons move around the nucleus in the hologram?",
                                                                "Fascinating question! Electrons don't actually orbit like planets - they exist in probability clouds called orbitals. Our hologram shows these quantum probability patterns where electrons are most likely to be found. It's one of the strangest discoveries in physics - particles that exist in multiple places at once until observed!"),
                                                new VideoQAItem("Master, what makes gravitons so special?",
                                                                "Gravitons are still theoretical - we haven't detected them yet! They would be the particles that carry gravitational force, just like photons carry light. If they exist, they travel at the speed of light and have no mass. Our hologram shows how they might interact with matter to create the gravitational pull that keeps us on Earth."),
                                                new VideoQAItem("How do these particles create all the matter we see?",
                                                                "It's like cosmic chemistry! Protons and neutrons cluster together to form atomic nuclei, electrons are attracted to orbit around them, creating atoms. Different combinations create different elements - hydrogen, carbon, oxygen, and everything else. Our hologram demonstrates how these simple particles combine to build complex matter through electromagnetic and nuclear forces.")))
                                .relatedVideos(new ArrayList<>()) // Will be populated later
                                .createdAt(getRandomCreationDate().toString())
                                .build());

                // Diamond 360 Hologram
                i++;
                title = "Diamond 360 degree";
                videos.add(VideoDetail.builder()
                                .id(String.valueOf(videos.size() + i))
                                .slug(generateSlugFromTitle(title))
                                .title(title)
                                .thumbnail("https://4cs.gia.edu/wp-content/uploads/2016/12/GIA_diamond_faceup-hero.jpg")
                                .summary("Marvel at the breathtaking brilliance of diamonds in this spectacular 360-degree holographic showcase. Watch as light dances through perfectly cut facets, creating rainbow refractions and sparkling fire from every angle. This immersive 3D experience lets you explore diamond anatomy, witness the interplay of light and crystal structure, and appreciate the geometric perfection that makes diamonds the ultimate symbol of beauty and luxury.")
                                .videoUrl("https://www.youtube.com/embed/YjyyVTek5FE?si=ihLG1bYBL2B4ByY-")
                                .category(categoryRepo.findById("science"))
                                .tags(List.of(
                                                buildTag("hologram", "Hologram"),
                                                buildTag("360-degree", "360 Degree")))
                                .likeCount(random.nextInt(1500))
                                .qaContent(List.of(
                                                new VideoQAItem("Master, what makes diamonds sparkle so brilliantly in the hologram?",
                                                                "Wonderful question! Diamonds have exceptional optical properties - they bend light more than almost any other material. When light enters a diamond, it bounces around inside due to total internal reflection before emerging as brilliant white light and colorful fire. Our 360-degree hologram shows exactly how each facet contributes to this dazzling display.",
                                                                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b7/Rough_Diamond.jpg/960px-Rough_Diamond.jpg"),
                                                new VideoQAItem("Why do diamonds have so many facets?",
                                                                "Great observation! Each facet is precisely angled to maximize light performance. A round brilliant cut has 57 or 58 facets, each calculated to reflect light back to your eye rather than letting it escape. In our hologram, you can see how light travels through the crown, bounces off the pavilion facets, and returns as brilliance and fire."),
                                                new VideoQAItem("How does the 360-degree view help us understand diamond quality?",
                                                                "Excellent question! Seeing a diamond from all angles reveals its true character. You can observe how well-proportioned facets create optimal light return, spot any inclusions or blemishes, and appreciate the symmetry that affects beauty. Our hologram lets you examine aspects that are impossible to see in a traditional setting."),
                                                new VideoQAItem("Master, what's the difference between brilliance and fire in the hologram?",
                                                                "Brilliant question! Brilliance is the white light that reflects back, while fire refers to the colorful flashes you see when light disperses into rainbow colors. Our hologram separates these effects so you can see how diamond cut affects both - well-cut diamonds maximize both brilliance and fire for stunning visual impact."),
                                                new VideoQAItem("Can you see the diamond's crystal structure in the hologram?",
                                                                "Indeed! Diamonds have a cubic crystal structure where each carbon atom bonds with four others in a tetrahedral arrangement. This creates the incredible hardness and optical properties we admire. Our hologram can zoom into the molecular level, showing how this perfect atomic lattice creates the foundation for diamond's unique characteristics.",
                                                                "https://uploads.nationaljeweler.com/uploads/00b57de3d9717e59d266949f0ac4b5b1.jpg")))
                                .relatedVideos(new ArrayList<>()) // Will be populated later
                                .createdAt(getRandomCreationDate().toString())
                                .build());

                // The Sun
                i++;
                title = "The Sun";
                videos.add(VideoDetail.builder()
                                .id(String.valueOf(videos.size() + i))
                                .slug(generateSlugFromTitle(title))
                                .title(title)
                                .thumbnail("https://cdn.hswstatic.com/gif/gettyimages-1406174121.jpg")
                                .summary("Witness the magnificent power and beauty of our closest star in this awe-inspiring holographic display. Experience the Sun's roiling plasma surface, spectacular solar flares, and swirling magnetic fields in stunning 3D detail. This hologram brings you face-to-face with the nuclear furnace that powers our solar system, showcasing solar prominences, coronal mass ejections, and the intricate dance of charged particles that create our star's dynamic atmosphere.")
                                .videoUrl("https://www.youtube.com/embed/e5DOApUtf7M?si=0v9Jg3ZKx7W8r6d2")
                                .category(categoryRepo.findById("science"))
                                .tags(List.of(
                                                buildTag("sun", "Sun"),
                                                buildTag("universe", "Universe")))
                                .likeCount(random.nextInt(1500))
                                .qaContent(List.of(
                                                new VideoQAItem("Master, what creates those beautiful flames we see on the Sun?",
                                                                "Those are solar prominences and flares! They're created by the Sun's powerful magnetic fields twisting and snapping, launching plasma thousands of miles into space. The temperatures reach millions of degrees, making them glow brilliantly. Our hologram shows how these magnetic field lines can suddenly reconnect, releasing enormous energy in spectacular displays.",
                                                                "https://www.chemistryviews.org/wp-content/uploads/2023/06/2023_ColorOfSun.png"),
                                                new VideoQAItem("How hot is the Sun's surface in the hologram?",
                                                                "The visible surface, called the photosphere, is about 5,500°C - hot enough to vaporize any known material instantly! But here's something amazing - the Sun's corona, its outer atmosphere that you can see in our hologram, is actually much hotter at over 1 million°C. Scientists are still trying to understand this temperature mystery!"),
                                                new VideoQAItem("Why does the Sun's surface look like it's bubbling?",
                                                                "Excellent observation! Those are convection cells called granulation - hot plasma rising from the interior and cooler plasma sinking back down, just like boiling water but on a massive scale. Each 'bubble' is about the size of Texas! Our 3D hologram shows this constant churning motion that brings energy from the Sun's core to its surface.",
                                                                "https://cdn.mos.cms.futurecdn.net/mzvdREkye2SgLCAqAme7fY-1200-80.jpg"),
                                                new VideoQAItem("Master, how does the Sun make its energy?",
                                                                "At the Sun's core, hydrogen atoms fuse together to create helium in a process called nuclear fusion. This releases tremendous energy that takes thousands of years to travel from the core to the surface! Every second, the Sun converts 4 million tons of matter into pure energy. Our hologram visualizes this incredible power source that has been shining for 4.6 billion years."),
                                                new VideoQAItem("Can solar flares really affect Earth?",
                                                                "Absolutely! Large solar flares and coronal mass ejections can disrupt satellites, power grids, and communication systems on Earth. They also create the beautiful auroras at the poles when charged particles interact with our magnetic field. Our hologram shows how these solar events travel through space - it's like the Sun sneezing, and we feel it 93 million miles away!",
                                                                "https://www.thoughtco.com/thmb/Vo0-sNs3PfSnIHvXQJSzRTlVszY=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/462977main_sun_layers_full-5a83345e875db90037f173c3.jpg")))
                                .relatedVideos(new ArrayList<>()) // Will be populated later
                                .createdAt(getRandomCreationDate().toString())
                                .build());

                // Morphing Geometry
                i++;
                title = "Morphing Geometry";
                videos.add(VideoDetail.builder()
                                .id(String.valueOf(videos.size() + i))
                                .slug(generateSlugFromTitle(title))
                                .title(title)
                                .thumbnail("https://images.vexels.com/media/users/3/138498/isolated/preview/8131ff2f2f78353ba82992d636a6fafe-geometric-sacred-geometry.png")
                                .summary("Enter the hypnotic world of morphing geometry where mathematical shapes transform, evolve, and dance in endless patterns. Watch as polygons seamlessly flow into circles, cubes unfold into complex polyhedra, and fractals grow in infinite recursive beauty. This mesmerizing holographic display showcases the fluid nature of geometric forms, revealing the hidden connections between mathematical concepts and the elegance of spatial transformations.")
                                .videoUrl("https://www.youtube.com/embed/ZHqbpCCYiDM?si=2Vi4eklIW2KXCdu9")
                                .category(categoryRepo.findById("science"))
                                .tags(List.of(
                                                buildTag("geometry", "Geometry"),
                                                buildTag("math", "Math")))
                                .likeCount(random.nextInt(1500))
                                .qaContent(List.of(
                                                new VideoQAItem("Master, how do shapes transform so smoothly in the hologram?",
                                                                "Excellent question! This uses mathematical interpolation - we calculate intermediate points between two shapes and smoothly transition through all the steps. It's like mathematical animation where each frame shows a slight change in the geometry. The computer calculates thousands of tiny transformations to create the fluid motion you see."),
                                                new VideoQAItem("Why do some geometric patterns repeat infinitely?",
                                                                "Those are fractals! They're mathematical objects that contain self-similar patterns at every scale. When you zoom into a fractal, you see the same pattern repeating. In our hologram, you can watch how simple rules create incredibly complex and beautiful patterns that continue forever - it's like nature's own programming language."),
                                                new VideoQAItem("How are these morphing shapes related to real-world applications?",
                                                                "Great observation! Morphing geometry is used everywhere - in computer graphics for movies, architectural design, medical imaging, and even in understanding how proteins fold in biology. Our hologram demonstrates principles that help engineers design car aerodynamics, create special effects, and model complex scientific phenomena."),
                                                new VideoQAItem("Master, what makes certain geometric transformations more beautiful than others?",
                                                                "Fascinating question! Beauty in geometry often comes from mathematical harmony - golden ratios, symmetry, and smooth transitions that our brains find pleasing. The transformations that follow mathematical principles like topology and group theory tend to create the most elegant patterns. It's where art meets mathematics!"),
                                                new VideoQAItem("Can these geometric patterns predict natural phenomena?",
                                                                "Absolutely! Nature follows geometric principles everywhere - from the spiral of galaxies to the branching of trees to the formation of crystals. The morphing patterns in our hologram demonstrate mathematical rules that govern how things grow, flow, and organize in the natural world. Mathematics is nature's blueprint!")))
                                .relatedVideos(new ArrayList<>()) // Will be populated later
                                .createdAt(getRandomCreationDate().toString())
                                .build());

                return videos;
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
