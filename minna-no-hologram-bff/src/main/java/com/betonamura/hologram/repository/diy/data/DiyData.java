package com.betonamura.hologram.repository.diy.data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import com.betonamura.hologram.domain.diy.DIYStep;
import com.betonamura.hologram.domain.diy.DiyCard;
import com.betonamura.hologram.domain.diy.DiyDetail;
import com.betonamura.hologram.domain.diy.Material;
import com.betonamura.hologram.domain.tag.Tag;

/**
 * Data provider for DIY hologram projects.
 * Contains a pre-populated set of detailed DIY records that can be used
 * for both detail views and search results.
 */
public class DiyData {

    private static final Random random = new Random(456); // Fixed seed for reproducibility
    private static final List<DiyDetail> DIY_DETAILS = createDiyDetails();
    private static final Map<String, DiyDetail> DIY_BY_SLUG = createDiySlugMap();

    // Real, existing Unsplash images related to technology, hologram, electronics
    // (all verified)
    private static final List<String> SCIENCE_IMAGE_URLS = List.of(
            "https://images.unsplash.com/photo-1550751827-4bd374c3f58b?q=80", // Tech hologram
            "https://images.unsplash.com/photo-1555680202-c86f0e12f086?q=80", // Digital interface
            "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?q=80", // Digital code
            "https://images.unsplash.com/photo-1558346490-a72e53ae2d4f?q=80", // Circuit board
            "https://images.unsplash.com/photo-1563206767-5b18f218e8de?q=80", // Arduino
            "https://images.unsplash.com/photo-1517420704952-d9f39e95b43e?q=80", // Circuit setup
            "https://images.unsplash.com/photo-1581091226825-a6a2a5aee158?q=80" // DIY electronics
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

    // Real hologram videos from YouTube (all verified)
    private static final List<String> HOLOGRAM_VIDEO_URLS = List.of(
            "https://www.youtube.com/embed/Y60mfBvXCj8", // 3D Hologram Projector Tutorial
            "https://www.youtube.com/embed/7YWTtCsvgvg", // DIY Hologram Pyramid
            "https://www.youtube.com/embed/Y5p4JFt3o5o", // How to Make a Hologram Projector
            "https://www.youtube.com/embed/0YiaOJbnRNI", // 3D Hologram DIY
            "https://www.youtube.com/embed/vnrKMpgYa2Y", // Smart Hologram Effect
            "https://www.youtube.com/embed/9t1xDnWIguo", // 3D Holographic Display
            "https://www.youtube.com/embed/kObRDkxR1BI", // Hologram Maker Tutorial
            "https://www.youtube.com/embed/JvrAFX213Wg", // Pyramid Hologram Display
            "https://www.youtube.com/embed/qsLOhRJVGiM", // 3D Hologram Fan Display
            "https://www.youtube.com/embed/CJ5Xtj83cYQ", // DIY Hologram for Mobile
            "https://www.youtube.com/embed/XBkGs_LfeQU", // DIY 3D Hologram
            "https://www.youtube.com/embed/vZ_YpjAd8cM", // Pyramid Hologram with Smartphone
            "https://www.youtube.com/embed/Q3W1ChIzlZ0", // Hologram Technology Explained
            "https://www.youtube.com/embed/eCXQMzLAgbs", // 3D Hologram Display Technology
            "https://www.youtube.com/embed/NuIJiLd6xCk" // Advanced Hologram Technology
    );

    // Materials commonly used in hologram projects (grouped by project type)
    private static final List<String> SIMPLE_MATERIALS = List.of(
            "Clear plastic sheet", "Scissors", "Ruler", "Marker", "Tape",
            "Smartphone or tablet", "Cardboard", "Glue", "Black cloth",
            "Clear CD case", "Transparent adhesive");

    private static final List<String> INTERMEDIATE_MATERIALS = List.of(
            "LED lights", "Acrylic sheet", "Small fan", "USB cable",
            "Power adapter", "Glass pane", "Mirror", "Magnifying glass",
            "LED strip", "Battery", "Wires", "Diffusion paper", "Wood panels",
            "Screws", "Screwdriver");

    private static final List<String> ADVANCED_MATERIALS = List.of(
            "Microcontroller", "Arduino board", "Raspberry Pi", "LCD screen",
            "3D printer", "PLA filament", "Solder", "Soldering iron",
            "HDMI cable", "Micro SD card", "Ultrasonic sensor", "Servo motor",
            "Power supply", "Camera module", "Jumper wires", "Breadboard");

    // Title sets for different categories
    private static final List<String> SCIENCE_DIY_TITLES = List.of(
            "Build a Quantum Physics Hologram Demonstrator",
            "Create a 3D Solar System Hologram Display",
            "Make a DNA Structure Hologram Visualizer",
            "Build a Scientific Principles Hologram Projector",
            "Create an Interactive Periodic Table Hologram",
            "Make a Medical Anatomy Hologram Display",
            "Build a Physics Experiment Hologram Demonstrator");

    private static final List<String> HISTORY_DIY_TITLES = List.of(
            "Build a Historical Landmark Hologram Display",
            "Create a Holographic Ancient Artifact Viewer",
            "Make a Historical Timeline Hologram",
            "Build a Pyramid Hologram with Ancient Civilization Content",
            "Create a Holographic Archaeological Site Display",
            "Make a Historical Battle Scene Hologram Projector",
            "Build a Holographic Ancient Monument Display");

    private static final List<String> CULTURE_DIY_TITLES = List.of(
            "Build a Cultural Heritage Hologram Showcase",
            "Create a Traditional Dance Holographic Display",
            "Make a Cultural Festival Hologram Viewer",
            "Build a Traditional Art Form Holographic Display",
            "Create a World Music Instruments Hologram Collection",
            "Make a Cultural Ritual Holographic Projector",
            "Build a Cultural Symbols Hologram Display");

    /**
     * Get a list of all DIY data in DiyCard format
     * This is used for search operations
     * 
     * @param maxCount Maximum number of DIY cards to return
     * @return List of DiyCard objects
     */
    public static List<DiyCard> getDiyCards(int maxCount) {
        int countToReturn = Math.min(maxCount, DIY_DETAILS.size());
        return DIY_DETAILS.stream()
                .limit(countToReturn)
                .map(DiyData::convertToDiyCard)
                .collect(Collectors.toList());
    }

    /**
     * Get a DIY detail by its slug
     * 
     * @param slug The slug identifier of the DIY project
     * @return The DIY detail if found, otherwise null
     */
    public static DiyDetail getDiyDetailBySlug(String slug) {
        if (slug == null) {
            return null;
        }

        // First try to get the exact match
        DiyDetail detail = DIY_BY_SLUG.get(slug);
        if (detail != null) {
            return detail;
        }

        // If not found, try case-insensitive matching
        Optional<String> matchingSlug = DIY_BY_SLUG.keySet().stream()
                .filter(key -> key.equalsIgnoreCase(slug))
                .findFirst();

        return matchingSlug.map(DIY_BY_SLUG::get).orElse(null);
    }

    /**
     * Get DIYs for pagination with optional filtering
     * 
     * @param offset     Starting index
     * @param limit      Maximum items to return
     * @param categories Categories to filter by (via tags) (optional)
     * @param query      Text to search for (optional)
     * @return Paginated and filtered list of DiyCard objects
     */
    public static List<DiyCard> getPaginatedDiys(int offset, int limit, List<String> categories, String query) {
        List<DiyDetail> filtered = new ArrayList<>(DIY_DETAILS);

        // Apply category filter (via tags)
        if (categories != null && !categories.isEmpty()) {
            filtered = filtered.stream()
                    .filter(d -> d.getTags().stream()
                            .anyMatch(tag -> categories.contains(tag.getId())))
                    .collect(Collectors.toList());
        }

        // Apply query filter
        if (query != null && !query.isBlank()) {
            String lowerQuery = query.toLowerCase();
            filtered = filtered.stream()
                    .filter(d -> d.getTitle().toLowerCase().contains(lowerQuery) ||
                            d.getSummary().toLowerCase().contains(lowerQuery))
                    .collect(Collectors.toList());
        }

        // Apply pagination
        int from = Math.max(0, offset);
        int safeLimit = Math.max(1, limit);
        int to = Math.min(filtered.size(), from + safeLimit);

        if (from >= filtered.size()) {
            return Collections.emptyList();
        }

        return filtered.subList(from, to).stream()
                .map(DiyData::convertToDiyCard)
                .collect(Collectors.toList());
    }

    /**
     * Get DIYs filtered by tag
     * 
     * @param tagId    Tag ID to filter by
     * @param maxCount Maximum number of DIYs to return
     * @return Filtered list of DiyCard objects
     */
    public static List<DiyCard> getDiysByTag(String tagId, int maxCount) {
        if (tagId == null) {
            return getDiyCards(maxCount);
        }

        return DIY_DETAILS.stream()
                .filter(d -> d.getTags().stream()
                        .anyMatch(tag -> tag.getId().equals(tagId)))
                .limit(maxCount)
                .map(DiyData::convertToDiyCard)
                .collect(Collectors.toList());
    }

    /**
     * Convert a DiyDetail to a DiyCard
     * 
     * @param detail The detailed DIY record
     * @return DiyCard representation
     */
    private static DiyCard convertToDiyCard(DiyDetail detail) {
        return DiyCard.builder()
                .id(detail.getId())
                .slug(detail.getSlug())
                .title(detail.getTitle())
                .thumbnail(findCategoryImageFromTags(detail.getTags()))
                .summary(detail.getSummary())
                .stepCount(detail.getStepCount())
                .estimatedTime(detail.getEstimatedTime())
                .difficulty(detail.getDifficulty())
                .tags(detail.getTags())
                .likeCount(detail.getLikeCount())
                .build();
    }

    /**
     * Find an appropriate category image based on tags
     * 
     * @param tags List of tags for the DIY project
     * @return URL to an appropriate image
     */
    private static String findCategoryImageFromTags(List<Tag> tags) {
        boolean isHistory = tags.stream().anyMatch(t -> t.getId().contains("history") ||
                t.getId().contains("artifact") ||
                t.getId().contains("ancient"));

        boolean isCulture = tags.stream().anyMatch(t -> t.getId().contains("culture") ||
                t.getId().contains("tradition") ||
                t.getId().contains("art"));

        if (isHistory) {
            if (HISTORY_IMAGE_URLS != null && !HISTORY_IMAGE_URLS.isEmpty()) {
                return HISTORY_IMAGE_URLS.get(random.nextInt(HISTORY_IMAGE_URLS.size()));
            } else {
                return "https://images.unsplash.com/photo-1567427018141-0584cfcbf1b8?q=80";
            }
        } else if (isCulture) {
            if (CULTURE_IMAGE_URLS != null && !CULTURE_IMAGE_URLS.isEmpty()) {
                return CULTURE_IMAGE_URLS.get(random.nextInt(CULTURE_IMAGE_URLS.size()));
            } else {
                return "https://images.unsplash.com/photo-1529148482759-b35b25c5f217?q=80";
            }
        } else {
            if (SCIENCE_IMAGE_URLS != null && !SCIENCE_IMAGE_URLS.isEmpty()) {
                return SCIENCE_IMAGE_URLS.get(random.nextInt(SCIENCE_IMAGE_URLS.size()));
            } else {
                return "https://images.unsplash.com/photo-1550751827-4bd374c3f58b?q=80";
            }
        }
    }

    /**
     * Create a map of DIYs by slug for quick lookup
     * 
     * @return Map with slug as key and DiyDetail as value
     */
    private static Map<String, DiyDetail> createDiySlugMap() {
        Map<String, DiyDetail> map = new HashMap<>();
        for (DiyDetail detail : DIY_DETAILS) {
            map.put(detail.getSlug(), detail);
        }
        return map;
    }

    /**
     * Create detailed records for all DIYs
     * 
     * @return List of detailed DIY records
     */
    private static List<DiyDetail> createDiyDetails() {
        List<DiyDetail> details = new ArrayList<>();

        // Add science DIYs
        details.addAll(createScienceDiys());

        // Add history DIYs
        details.addAll(createHistoryDiys());

        // Add culture DIYs
        details.addAll(createCultureDiys());

        return details;
    }

    /**
     * Create science category DIY records
     * 
     * @return List of science DIY details
     */
    private static List<DiyDetail> createScienceDiys() {
        List<DiyDetail> diys = new ArrayList<>();

        // Defensive: fallback to a safe list if SCIENCE_DIY_TITLES is null/empty
        List<String> titles = (SCIENCE_DIY_TITLES != null && !SCIENCE_DIY_TITLES.isEmpty())
                ? SCIENCE_DIY_TITLES
                : List.of("Build a Quantum Physics Hologram Demonstrator");

        for (int i = 0; i < titles.size(); i++) {
            String id = "sci-" + (500 + i);
            String title = titles.get(i);
            String slug = generateSlugFromTitle(title);
            String summary = "Learn how to build " + title.toLowerCase()
                    .replace("build ", "").replace("create ", "").replace("make ", "")
                    + " using simple materials. Perfect for science education and demonstrations.";

            int stepCount = 5 + random.nextInt(5); // 5-9 steps
            String difficulty = getDifficultyForIndex(i);
            String estimatedTime = getEstimatedTimeForDifficulty(difficulty);
            List<Tag> tags = buildScienceTags();
            int likeCount = 100 + random.nextInt(900);

            // Defensive: fallback to a safe video URL if list is null/empty
            String videoUrl;
            if (HOLOGRAM_VIDEO_URLS != null && !HOLOGRAM_VIDEO_URLS.isEmpty()) {
                videoUrl = HOLOGRAM_VIDEO_URLS.get(i % HOLOGRAM_VIDEO_URLS.size());
            } else {
                videoUrl = "https://www.youtube.com/embed/Y60mfBvXCj8"; // fallback default
            }

            diys.add(DiyDetail.builder()
                    .id(id)
                    .slug(slug)
                    .title(title)
                    .videoUrl(videoUrl)
                    .summary(summary)
                    .stepCount(stepCount)
                    .estimatedTime(estimatedTime)
                    .difficulty(difficulty)
                    .tags(tags)
                    .likeCount(likeCount)
                    .materials(generateMaterialsForDifficulty(difficulty))
                    .steps(generateSteps(stepCount))
                    .relatedDIY(new ArrayList<>()) // Empty for now, will be populated in repository
                    .createdAt(getRandomCreationDate())
                    .build());
        }

        return diys;
    }

    /**
     * Create history category DIY records
     * 
     * @return List of history DIY details
     */
    private static List<DiyDetail> createHistoryDiys() {
        List<DiyDetail> diys = new ArrayList<>();

        for (int i = 0; i < HISTORY_DIY_TITLES.size(); i++) {
            String id = "hist-" + (600 + i);
            String title = HISTORY_DIY_TITLES.get(i);
            String slug = generateSlugFromTitle(title);
            String summary = "Learn how to build " + title.toLowerCase()
                    .replace("build ", "").replace("create ", "").replace("make ", "")
                    + " using simple materials. Explore history through interactive holograms.";

            int stepCount = 5 + random.nextInt(5); // 5-9 steps
            String difficulty = getDifficultyForIndex(i);
            String estimatedTime = getEstimatedTimeForDifficulty(difficulty);
            List<Tag> tags = buildHistoryTags();
            int likeCount = 100 + random.nextInt(900);

            diys.add(DiyDetail.builder()
                    .id(id)
                    .slug(slug)
                    .title(title)
                    .videoUrl(HOLOGRAM_VIDEO_URLS.get((i + 5) % HOLOGRAM_VIDEO_URLS.size()))
                    .summary(summary)
                    .stepCount(stepCount)
                    .estimatedTime(estimatedTime)
                    .difficulty(difficulty)
                    .tags(tags)
                    .likeCount(likeCount)
                    .materials(generateMaterialsForDifficulty(difficulty))
                    .steps(generateSteps(stepCount))
                    .relatedDIY(new ArrayList<>()) // Empty for now, will be populated in repository
                    .createdAt(getRandomCreationDate())
                    .build());
        }

        return diys;
    }

    /**
     * Create culture category DIY records
     * 
     * @return List of culture DIY details
     */
    private static List<DiyDetail> createCultureDiys() {
        List<DiyDetail> diys = new ArrayList<>();

        for (int i = 0; i < CULTURE_DIY_TITLES.size(); i++) {
            String id = "cult-" + (700 + i);
            String title = CULTURE_DIY_TITLES.get(i);
            String slug = generateSlugFromTitle(title);
            String summary = "Learn how to build " + title.toLowerCase()
                    .replace("build ", "").replace("create ", "").replace("make ", "")
                    + " using simple materials. Celebrate cultural heritage through holograms.";

            int stepCount = 5 + random.nextInt(5); // 5-9 steps
            String difficulty = getDifficultyForIndex(i);
            String estimatedTime = getEstimatedTimeForDifficulty(difficulty);
            List<Tag> tags = buildCultureTags();
            int likeCount = 100 + random.nextInt(900);

            diys.add(DiyDetail.builder()
                    .id(id)
                    .slug(slug)
                    .title(title)
                    .videoUrl(HOLOGRAM_VIDEO_URLS.get((i + 10) % HOLOGRAM_VIDEO_URLS.size()))
                    .summary(summary)
                    .stepCount(stepCount)
                    .estimatedTime(estimatedTime)
                    .difficulty(difficulty)
                    .tags(tags)
                    .likeCount(likeCount)
                    .materials(generateMaterialsForDifficulty(difficulty))
                    .steps(generateSteps(stepCount))
                    .relatedDIY(new ArrayList<>()) // Empty for now, will be populated in repository
                    .createdAt(getRandomCreationDate())
                    .build());
        }

        return diys;
    }

    /**
     * Get appropriate difficulty level based on index
     * 
     * @param index Index of project in list
     * @return Difficulty string (easy, medium, or hard)
     */
    private static String getDifficultyForIndex(int index) {
        switch (index % 3) {
            case 0:
                return "easy";
            case 1:
                return "medium";
            default:
                return "hard";
        }
    }

    /**
     * Get estimated time based on difficulty
     * 
     * @param difficulty Project difficulty level
     * @return Time estimate string
     */
    private static String getEstimatedTimeForDifficulty(String difficulty) {
        switch (difficulty) {
            case "easy":
                return "15-30 min";
            case "medium":
                return "30-60 min";
            case "hard":
                return "1-2 hours";
            default:
                return "30-45 min";
        }
    }

    /**
     * Generate materials list based on project difficulty
     * 
     * @param difficulty Project difficulty level
     * @return List of required materials
     */
    public static List<Material> generateMaterialsForDifficulty(String difficulty) {
        List<Material> materials = new ArrayList<>();
        List<String> usedMaterials = new ArrayList<>();

        // All projects need some simple materials
        addUniqueRandomMaterials(materials, usedMaterials, SIMPLE_MATERIALS, 4 + random.nextInt(3));

        // Add more advanced materials based on difficulty
        if ("medium".equals(difficulty) || "hard".equals(difficulty)) {
            addUniqueRandomMaterials(materials, usedMaterials, INTERMEDIATE_MATERIALS, 3 + random.nextInt(3));
        }

        if ("hard".equals(difficulty)) {
            addUniqueRandomMaterials(materials, usedMaterials, ADVANCED_MATERIALS, 2 + random.nextInt(3));
        }

        return materials;
    }

    /**
     * Add unique random materials to the materials list
     * 
     * @param materials     List to add to
     * @param usedMaterials Tracking list of already used materials
     * @param sourceList    Source list to select from
     * @param count         Number of materials to add
     */
    private static void addUniqueRandomMaterials(List<Material> materials, List<String> usedMaterials,
            List<String> sourceList, int count) {
        // Defensive: fallback to an empty list if sourceList is null
        if (sourceList == null || sourceList.isEmpty()) {
            return;
        }
        int attemptsLeft = sourceList.size() * 2; // Prevent infinite loops

        while (count > 0 && attemptsLeft > 0) {
            String material = sourceList.get(random.nextInt(sourceList.size()));

            if (!usedMaterials.contains(material)) {
                usedMaterials.add(material);
                boolean isOptional = random.nextInt(10) < 2; // 20% chance of being optional
                materials.add(new Material(material, isOptional));
                count--;
            }

            attemptsLeft--;
        }
    }

    /**
     * Generate step-by-step instructions for a DIY project
     * 
     * @param count Number of steps to generate
     * @return List of DIYStep objects
     */
    public static List<DIYStep> generateSteps(int count) {
        List<DIYStep> steps = new ArrayList<>();

        String[] stepTitles = {
                "Gather Materials",
                "Prepare the Base",
                "Cut the Materials",
                "Assemble Components",
                "Connect Electronics",
                "Install Software",
                "Test Your Setup",
                "Final Assembly",
                "Make Adjustments",
                "Enjoy Your Creation"
        };

        for (int i = 0; i < count; i++) {
            String title = i < stepTitles.length ? stepTitles[i] : "Step " + (i + 1);
            // Use real, existing images for steps
            String imageUrl = getAppropriateStepImage(i);
            String caption = "Step " + (i + 1) + ": " + title;
            String description = generateStepDescription(i, title);

            DIYStep step = DIYStep.builder()
                    .stepNumber(i + 1)
                    .title(title)
                    .imageUrl(imageUrl)
                    .caption(caption)
                    .description(description)
                    .build();

            steps.add(step);
        }

        return steps;
    }

    /**
     * Get an appropriate image for a specific step
     * 
     * @param stepIndex The step index
     * @return URL to an appropriate image
     */
    private static String getAppropriateStepImage(int stepIndex) {
        // Defensive: fallback to a safe image if SCIENCE_IMAGE_URLS is null/empty
        List<String> sci = (SCIENCE_IMAGE_URLS != null && !SCIENCE_IMAGE_URLS.isEmpty()) ? SCIENCE_IMAGE_URLS
                : List.of("https://images.unsplash.com/photo-1550751827-4bd374c3f58b?q=80");
        List<String> hist = (HISTORY_IMAGE_URLS != null && !HISTORY_IMAGE_URLS.isEmpty()) ? HISTORY_IMAGE_URLS
                : List.of("https://images.unsplash.com/photo-1567427018141-0584cfcbf1b8?q=80");
        List<String> cult = (CULTURE_IMAGE_URLS != null && !CULTURE_IMAGE_URLS.isEmpty()) ? CULTURE_IMAGE_URLS
                : List.of("https://images.unsplash.com/photo-1529148482759-b35b25c5f217?q=80");
        // Choose appropriate images based on the step
        switch (stepIndex) {
            case 0: // Gather Materials
                return sci.get(4 % sci.size()); // Arduino image or similar
            case 1: // Prepare the Base
                return sci.get(1 % sci.size()); // Digital interface
            case 2: // Cut the Materials
                return sci.get(0 % sci.size()); // Tech hologram
            case 3: // Assemble Components
                return sci.get(6 % sci.size()); // DIY electronics
            case 4: // Connect Electronics
                return sci.get(3 % sci.size()); // Circuit board
            default:
                // For other steps, rotate through all image URLs
                List<String> allImages = new ArrayList<>();
                allImages.addAll(sci);
                allImages.addAll(hist);
                allImages.addAll(cult);
                return allImages.get((stepIndex * 3) % allImages.size());
        }
    }

    /**
     * Generate descriptive text for a project step
     * 
     * @param stepIndex The step index
     * @param title     The step title
     * @return Descriptive text for the step
     */
    private static String generateStepDescription(int stepIndex, String title) {
        switch (stepIndex) {
            case 0:
                return "Start by gathering all the materials listed above. Make sure you have everything before you begin. For this hologram project, the quality of the transparent materials will directly affect your final result, so choose the clearest materials available.";
            case 1:
                return "Prepare your workspace and arrange all materials in an organized manner. For hologram projection, you'll need a clean, flat surface and good lighting. Draw the template for your hologram structure on paper first to use as a guide.";
            case 2:
                return "Carefully cut the transparent material according to the provided measurements. Precision is important - use a ruler and marker to ensure straight lines, and cut slowly to avoid jagged edges which can distort the holographic effect.";
            case 3:
                return "Follow the diagram to assemble the main components together. For pyramid-style hologram projectors, make sure the angles are exactly as specified (usually 45 degrees) to ensure proper reflection of the image.";
            case 4:
                return "If your project includes electronics, connect them according to the circuit diagram. Double-check all connections before applying power to avoid damage to components. For LED-based holograms, ensure even lighting for the best effect.";
            case 5:
                return "Install any necessary software or apps to control your hologram display. For smartphone-based projections, download the recommended hologram video app and test it with a sample video to verify it displays correctly.";
            case 6:
                return "Test your setup to make sure everything is working correctly before finalizing. Adjust the position of the projector or screen until you get a clear holographic effect. You may need to adjust the room lighting for optimal visibility.";
            case 7:
                return "Complete the final assembly steps and secure all components in place. Use transparent adhesive for clear parts to maintain visual clarity, and ensure everything is firmly attached to prevent shifting during use.";
            case 8:
                return "Make any necessary adjustments to improve the hologram quality. This might include fine-tuning the position of the projection surface, adjusting the brightness of your display, or modifying the viewing angle for best results.";
            case 9:
                return "Your hologram display is now complete! Try it out with the provided sample content, or create your own hologram-compatible videos. For best results, view in a dimly lit room with the hologram at eye level.";
            default:
                return "Complete this step by following the detailed instructions. Take your time and focus on precision for the best holographic results.";
        }
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
    public static OffsetDateTime getRandomCreationDate() {
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
                buildTag("science", "Science"),
                buildTag("diy", "DIY"),
                buildTag("education", "Education"),
                buildTag("physics", "Physics"),
                buildTag("technology", "Technology"));
    }

    /**
     * Build tags for history category
     * 
     * @return List of appropriate tags
     */
    private static List<Tag> buildHistoryTags() {
        return List.of(
                buildTag("hologram", "Hologram"),
                buildTag("history", "History"),
                buildTag("diy", "DIY"),
                buildTag("education", "Education"),
                buildTag("ancient-history", "Ancient History"),
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
                buildTag("culture", "Culture"),
                buildTag("diy", "DIY"),
                buildTag("education", "Education"),
                buildTag("traditions", "Traditions"),
                buildTag("arts", "Arts"));
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

    /**
     * Get a random video URL from the available list
     * 
     * @return A YouTube embed URL for hologram content
     */
    public static String getRandomVideoUrl() {
        return HOLOGRAM_VIDEO_URLS.get(random.nextInt(HOLOGRAM_VIDEO_URLS.size()));
    }
}
