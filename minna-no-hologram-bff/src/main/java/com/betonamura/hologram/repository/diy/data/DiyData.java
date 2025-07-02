package com.betonamura.hologram.repository.diy.data;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.util.StringUtils;

import com.betonamura.hologram.domain.diy.DIYStep;
import com.betonamura.hologram.domain.diy.DiyCard;
import com.betonamura.hologram.domain.diy.DiyDetail;
import com.betonamura.hologram.domain.diy.Material;
import com.betonamura.hologram.domain.tag.Tag;

/**
 * Data provider for DIY hologram projects.
 * Contains a pre-populated set of detailed DIY records that can be used
 * for both detail views and search results.
 * 
 * The focus is exclusively on providing instructions for building hologram
 * projectors at home, with step-by-step guidance appropriate for various
 * skill levels.
 */
public class DiyData {

    // Constants for difficulty levels
    public static final String DIFFICULTY_EASY = "easy";
    public static final String DIFFICULTY_MEDIUM = "medium";
    public static final String DIFFICULTY_HARD = "hard";

    // Constants for time estimates
    public static final String TIME_ESTIMATE_SHORT = "15-30 min";
    public static final String TIME_ESTIMATE_MEDIUM = "30-60 min";
    public static final String TIME_ESTIMATE_LONG = "1-2 hours";

    // Constants for hologram types
    public static final String TYPE_PYRAMID = "pyramid";
    public static final String TYPE_SMARTPHONE = "smartphone";
    public static final String TYPE_LED = "led";
    public static final String TYPE_ADVANCED = "advanced";

    // Constants for tag categories
    public static final String TAG_HOLOGRAM = "hologram";
    public static final String TAG_DIY = "diy";
    public static final String TAG_EDUCATIONAL = "education";

    private static final Random random = new Random(456); // Fixed seed for reproducibility

    // Verified images specifically related to hologram projector DIY projects
    private static final List<String> HOLOGRAM_IMAGE_URLS = List.of(
            "https://images.unsplash.com/photo-1550751827-4bd374c3f58b?q=80", // Tech hologram visualization
            "https://content.instructables.com/FBR/EVJD/IJQNY1D0/FBREVJDIJQNY1D0.jpg?auto=webp&frame=1&width=603&fit=bounds&md=MjAxNi0wMS0yMyAwODoxMTowMi4w", // Pyramid
                                                                                                                                                              // hologram
                                                                                                                                                              // setup
            "https://content.instructables.com/F8R/7BRE/IJQNY1D4/F8R7BREIJQNY1D4.jpg?auto=webp&frame=1&width=600&height=1024&fit=bounds&md=a687f9f31691fff8dcfcaaa69f0a4a9c", // Hologram
                                                                                                                                                                              // template
            "https://content.instructables.com/FBJ/Y52F/IJQNY1D5/FBJY52FIJQNY1D5.jpg?auto=webp&frame=1&width=600&height=1024&fit=bounds&md=06bbfc5bf8d052017c423901e21111c4", // Cutting
                                                                                                                                                                              // materials
            "https://content.instructables.com/FE0/ZZUD/IJQNY1DB/FE0ZZUDIJQNY1DB.jpg?auto=webp&frame=1&width=933&height=1024&fit=bounds&md=0087ff9dffbbe813be5b43873421ed67", // Assembly
            "https://content.instructables.com/FZQ/KZ1L/IJQNY1DF/FZQKZ1LIJQNY1DF.jpg?auto=webp&frame=1&width=933&height=1024&fit=bounds&md=6337f26e2f485838bf5b00ac44fb429f", // Display
                                                                                                                                                                              // setup
            "https://praxent.com/wp-content/uploads/2020/09/what-is-a-hologram.jpg", // Completed hologram
            "https://www.androidcentral.com/sites/androidcentral.com/files/styles/large/public/article_images/2020/07/hologram-projector-hero.jpg", // Testing
                                                                                                                                                    // hologram
            "https://images.unsplash.com/photo-1563206767-5b18f218e8de?q=80", // Arduino for advanced setup
            "https://images.unsplash.com/photo-1612815836745-64b9b77cf27b?q=80" // Phone display for hologram
    );

    // Real hologram videos from YouTube (all verified)
    private static final String DEFAULT_VIDEO_DIY_URL = "https://www.youtube.com/embed/Y60mfBvXCj8";
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

    // Materials commonly used in hologram projector projects (grouped by
    // complexity)
    private static final List<String> SIMPLE_MATERIALS = List.of(
            "Clear plastic sheet (transparency film)", "Scissors", "Ruler", "Fine-point marker", "Clear tape",
            "Smartphone or tablet", "Black cardboard", "Craft knife", "Protractor for 45° angles",
            "Empty CD case", "Graph paper for template", "Clear plastic bottle", "Glue stick",
            "Microfiber cloth for cleaning", "Black electrical tape", "LED flashlight");

    private static final List<String> INTERMEDIATE_MATERIALS = List.of(
            "Acrylic sheet (1-3mm thick)", "Glass cutting tool", "Addressable LED strip lights", "USB power bank",
            "Mini USB cable", "Clear plastic display box", "Anti-glare film", "Black foam board",
            "One-way mirror film", "Fresnel lens", "Light diffuser sheet", "Precision knife set",
            "Double-sided tape", "Hot glue gun", "Mirror finish spray paint",
            "Phone/tablet stand", "Hologram video converter app");

    private static final List<String> ADVANCED_MATERIALS = List.of(
            "Arduino Nano/Uno", "Raspberry Pi Zero", "Small LCD/OLED display", "Mini projector",
            "One-way mirror acrylic (4mm)", "Ultrasonic distance sensor", "RGB LED matrix", "Servo motor",
            "Thin Plexiglass sheets (0.5-1mm)", "Micro SD card with hologram videos", "PWM controller", "Jumper wires",
            "3D printer access (for custom pyramid frame)", "Clear PLA filament", "Motion sensor", "Mini speaker",
            "Fan for cooling", "Mirror film (98% reflective)", "Adjustable phone mount");

    // DIY hologram projector titles - focused exclusively on building methods
    private static final List<String> DIY_PROJECTOR_TITLES = List.of(
            "Build a Smartphone Pyramid Hologram Projector",
            "Create a 4-sided Hologram Display with CD Cases",
            "Make a Large-scale Hologram Projector for Group Viewing",
            "Build an Arduino-Controlled Hologram Display with LEDs",
            "Create a Floating Image Hologram Projector",
            "Make a Multi-layer Hologram Display for 3D Effects",
            "Build a High-Definition Hologram Projector with Acrylic Sheets",
            "Create a Portable Hologram Viewer for Smartphones",
            "Make a Foldable Hologram Pyramid for Easy Storage",
            "Build a Dual-Layer Hologram Projector for Depth Effect",
            "Create a 360° Hologram Display with Rotating Base",
            "Make an Eco-Friendly Hologram Display from Recycled Materials",
            "Build a Smartphone-powered Double Pyramid Hologram Viewer",
            "Create a Multi-angle Hologram Display for All-Around Viewing",
            "Make a Motion-Activated Hologram Projector with Sensors",
            "Build a Travel-friendly Folding Hologram Kit with Case",
            "Create a Tabletop Hologram Theater for Home Entertainment",
            "Make a Mini Hologram Projector for Your Desk",
            "Build a Magnified Hologram Display with Enhanced Clarity",
            "Create a Hologram Box with Interchangeable Display Panels",
            "Make a Hologram Fan Display with Spinning Effect");

    /**
     * Create detailed records for all DIYs
     * 
     * @return List of detailed DIY records
     */
    public static List<DiyDetail> getDiysDetail() {
        return createHologramProjectorDiys();
    }

    /**
     * Get a list of all DIY data in DiyCard format
     * This is used for search operations
     * 
     * @param maxCount Maximum number of DIY cards to return
     * @return List of DiyCard objects
     */
    public static List<DiyCard> getDiyCards(int maxCount) {
        final List<DiyDetail> diys = getDiysDetail();
        int countToReturn = Math.min(maxCount, diys.size());
        return diys.stream()
                .limit(countToReturn)
                .map(DiyData::toDiyCard)
                .collect(Collectors.toList());
    }

    /**
     * Get DIYs filtered by tag
     * 
     * @param tagId Tag ID to filter by
     * @param limit Maximum number of DIYs to return
     * @return Filtered list of DiyCard objects
     */
    public static List<DiyCard> getDiysByTag(final String tagId, final int limit) {
        if (!StringUtils.hasText(tagId)) {
            return getDiyCards(limit);
        }

        final List<DiyDetail> diys = getDiysDetail();
        return diys.stream()
                .filter(d -> d.getTags().stream()
                        .anyMatch(tag -> tag.getId().equals(tagId)))

                .limit(limit)
                .map(DiyData::toDiyCard)
                .collect(Collectors.toList());
    }

    /**
     * Convert a DiyDetail to a DiyCard
     * 
     * @param detail The detailed DIY record
     * @return DiyCard representation
     */
    public static DiyCard toDiyCard(DiyDetail detail) {
        return DiyCard.builder()
                .id(detail.getId())
                .slug(detail.getSlug())
                .title(detail.getTitle())
                .thumbnail(findImageFromTags(detail.getTags()))
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
    private static String findImageFromTags(final List<Tag> tags) {
        // Select image based on hologram type tags
        if (HOLOGRAM_IMAGE_URLS == null || HOLOGRAM_IMAGE_URLS.isEmpty()) {
            return "https://images.unsplash.com/photo-1550751827-4bd374c3f58b?q=80"; // Default failsafe image
        }

        // Select specific image based on hologram type if possible
        boolean isPyramid = tags.stream().anyMatch(t -> t.getId().equals(TYPE_PYRAMID) ||
                t.getId().contains("pyramid"));
        boolean isSmartphone = tags.stream().anyMatch(t -> t.getId().equals(TYPE_SMARTPHONE) ||
                t.getId().contains("smartphone") || t.getId().contains("phone"));
        boolean isAdvanced = tags.stream().anyMatch(t -> t.getId().equals(TYPE_ADVANCED) ||
                t.getId().contains("advanced") || t.getId().contains("arduino"));

        if (isPyramid) {
            return HOLOGRAM_IMAGE_URLS.get(1 % HOLOGRAM_IMAGE_URLS.size()); // Pyramid image
        } else if (isSmartphone) {
            return HOLOGRAM_IMAGE_URLS.get(9 % HOLOGRAM_IMAGE_URLS.size()); // Phone display image
        } else if (isAdvanced) {
            return HOLOGRAM_IMAGE_URLS.get(8 % HOLOGRAM_IMAGE_URLS.size()); // Arduino image
        } else {
            // Select random hologram image
            return HOLOGRAM_IMAGE_URLS.get(random.nextInt(HOLOGRAM_IMAGE_URLS.size()));
        }
    }

    /**
     * Create hologram projector DIY records with step-by-step instructions
     * 
     * @return List of hologram projector DIY details
     */
    private static List<DiyDetail> createHologramProjectorDiys() {
        List<DiyDetail> diys = new ArrayList<>();

        // Use our DIY projector titles list, or fallback to a default title if the list
        // is empty
        List<String> titles = (DIY_PROJECTOR_TITLES != null && !DIY_PROJECTOR_TITLES.isEmpty())
                ? DIY_PROJECTOR_TITLES
                : List.of("Build a Smartphone Pyramid Hologram Projector");

        for (int i = 0; i < titles.size(); i++) {
            // Create a unique ID based on the index
            String id = "diy-" + (100 + i);
            String title = titles.get(i);
            String slug = generateSlugFromTitle(title);

            // Determine the hologram type from the title
            String hologramType = determineHologramType(title);

            // Generate appropriate details for this DIY project
            int stepCount = 5 + random.nextInt(5); // 5-9 steps
            String difficulty = getDifficultyForIndex(i);
            String estimatedTime = getEstimatedTimeForDifficulty(difficulty);
            List<Tag> tags = generateDynamicTags(title, i, hologramType);
            int likeCount = 100 + random.nextInt(900);

            // Ensure we have a valid video URL, cycling through our verified list
            String videoUrl;
            if (HOLOGRAM_VIDEO_URLS != null && !HOLOGRAM_VIDEO_URLS.isEmpty()) {
                videoUrl = HOLOGRAM_VIDEO_URLS.get(i % HOLOGRAM_VIDEO_URLS.size());
            } else {
                videoUrl = DEFAULT_VIDEO_DIY_URL;
            }

            // Generate a detailed summary for this specific hologram projector
            String summary = generateProjectSummary(title, hologramType, i);

            // Build the complete DIY detail
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
                    .relatedDIY(new ArrayList<>())
                    .createdAt(getRandomCreationDate())
                    .build());
        }
        return diys;
    }

    /**
     * Determine the hologram type from the title to use for tagging and
     * categorization
     * 
     * @param title The DIY title
     * @return The hologram type (pyramid, smartphone, led, advanced)
     */
    private static String determineHologramType(String title) {
        String titleLower = title.toLowerCase();

        if (titleLower.contains("pyramid")) {
            return TYPE_PYRAMID;
        } else if (titleLower.contains("smartphone") || titleLower.contains("phone")) {
            return TYPE_SMARTPHONE;
        } else if (titleLower.contains("led") || titleLower.contains("arduino")) {
            return TYPE_LED;
        } else if (titleLower.contains("advanced") || titleLower.contains("motion") || titleLower.contains("sensor")) {
            return TYPE_ADVANCED;
        } else {
            return TYPE_PYRAMID; // Default type
        }
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
                return DIFFICULTY_EASY;
            case 1:
                return DIFFICULTY_MEDIUM;
            default:
                return DIFFICULTY_HARD;
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
            case DIFFICULTY_EASY:
                return TIME_ESTIMATE_SHORT;
            case DIFFICULTY_MEDIUM:
                return TIME_ESTIMATE_MEDIUM;
            case DIFFICULTY_HARD:
                return TIME_ESTIMATE_LONG;
            default:
                return TIME_ESTIMATE_MEDIUM;
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
        if (DIFFICULTY_MEDIUM.equals(difficulty) || DIFFICULTY_HARD.equals(difficulty)) {
            addUniqueRandomMaterials(materials, usedMaterials, INTERMEDIATE_MATERIALS, 3 + random.nextInt(3));
        }

        if (DIFFICULTY_HARD.equals(difficulty)) {
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
     * Generate step-by-step instructions for a DIY hologram projector
     * 
     * @param count Number of steps to generate
     * @return List of DIYStep objects with hologram-specific instructions
     */
    public static List<DIYStep> generateSteps(int count) {
        List<DIYStep> steps = new ArrayList<>();

        String[] stepTitles = {
                "Gather Hologram Materials",
                "Create the Hologram Template",
                "Cut the Transparent Panels",
                "Assemble the Pyramid Structure",
                "Set Up Lighting and Display Base",
                "Prepare Hologram Video Content",
                "Test the Hologram Projection",
                "Complete the Base and Housing",
                "Optimize the Hologram Effect",
                "Use and Share Your Hologram Projector"
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
    // Specific hologram tutorial images for each step in building a hologram
    // projector
    private static final List<String> STEP_IMAGES = List.of(
            "https://content.instructables.com/FBR/EVJD/IJQNY1D0/FBREVJDIJQNY1D0.jpg?auto=webp&frame=1&width=603&fit=bounds&md=MjAxNi0wMS0yMyAwODoxMTowMi4w", // Materials
            "https://content.instructables.com/F8R/7BRE/IJQNY1D4/F8R7BREIJQNY1D4.jpg?auto=webp&frame=1&width=600&height=1024&fit=bounds&md=a687f9f31691fff8dcfcaaa69f0a4a9c", // Template
            "https://content.instructables.com/FBJ/Y52F/IJQNY1D5/FBJY52FIJQNY1D5.jpg?auto=webp&frame=1&width=600&height=1024&fit=bounds&md=06bbfc5bf8d052017c423901e21111c4", // Cutting
            "https://content.instructables.com/FE0/ZZUD/IJQNY1DB/FE0ZZUDIJQNY1DB.jpg?auto=webp&frame=1&width=933&height=1024&fit=bounds&md=0087ff9dffbbe813be5b43873421ed67", // Assembly
            "https://content.instructables.com/FZQ/KZ1L/IJQNY1DF/FZQKZ1LIJQNY1DF.jpg?auto=webp&frame=1&width=933&height=1024&fit=bounds&md=6337f26e2f485838bf5b00ac44fb429f", // Setup
            "https://images.unsplash.com/photo-1611162617213-7d7a39e9b1d7?q=80&w=1974&auto=format&fit=crop", // Content
                                                                                                             // preparation
            "https://www.androidcentral.com/sites/androidcentral.com/files/styles/large/public/article_images/2020/07/hologram-projector-hero.jpg", // Testing
            "https://content.instructables.com/FVJ/3GUO/IJQNY1DB/FVJ3GUOIJQNY1DB.jpg?auto=webp&frame=1&width=933&height=1024&fit=bounds&md=211c9711abb79d9104f2f37982f20250", // Base
            "https://images.unsplash.com/photo-1550751827-4bd374c3f58b?q=80", // Optimization
            "https://praxent.com/wp-content/uploads/2020/09/what-is-a-hologram.jpg" // Final result
    );

    /**
     * Get an appropriate image for a specific step in the hologram projector
     * building process
     * 
     * @param stepIndex The step index
     * @return URL to an appropriate image for the specific step
     */
    private static String getAppropriateStepImage(int stepIndex) {
        // Defensive: fallback to a safe image if the specific step doesn't have an
        // image
        if (STEP_IMAGES == null || STEP_IMAGES.isEmpty() || stepIndex >= STEP_IMAGES.size()) {
            // Defensive: use the first hologram image or a default one
            if (HOLOGRAM_IMAGE_URLS != null && !HOLOGRAM_IMAGE_URLS.isEmpty()) {
                return HOLOGRAM_IMAGE_URLS.get(0);
            } else {
                return "https://images.unsplash.com/photo-1550751827-4bd374c3f58b?q=80";
            }
        }

        // Return the specific step image
        return STEP_IMAGES.get(stepIndex);
    }

    /**
     * Generate descriptive text for a hologram projector DIY step
     * 
     * @param stepIndex The step index
     * @param title     The step title
     * @return Detailed instruction text for the hologram projector step
     */
    private static String generateStepDescription(int stepIndex, String title) {
        switch (stepIndex) {
            case 0:
                return "Gather all listed materials. Use high-quality transparent materials (92%+ light transmission) with minimal scratches and no color tint. Clean all transparent pieces with a microfiber cloth to remove fingerprints and dust for optimal results.";
            case 1:
                return "Create the hologram template. For pyramid projectors, draw a trapezoid with a 1cm top edge (small) or 3-6cm (larger). Ensure sides slope at exactly 45° angles. Use the formula: height = (top edge + bottom edge) ÷ 2. Use graph paper or download our precision template from the app.";
            case 2:
                return "Transfer the template to your transparent material using a fine-point marker. Create four identical pieces for a 4-sided pyramid. Cut carefully along the lines—straight edges are essential for a crisp hologram effect. For acrylic, score multiple times before breaking along the line.";
            case 3:
                return "Assemble the pyramid by joining the sides. For plastic, use clear tape on outside edges; for acrylic, use adhesive or acrylic cement. Ensure a perfect point at top with equal sides at precisely 45° angles. Test the structure temporarily before final assembly.";
            case 4:
                return "Install LED lighting around the base if included. Position lights to illuminate the hologram without being visible in reflections. For smartphone projectors, create a flat platform with the pyramid centered above the screen. Maintain 1-2cm distance between screen and pyramid base.";
            case 5:
                return "Download hologram-compatible videos with the image repeated four times around a black background, each rotated 90°. Use our app to convert regular videos to hologram format, or find free ones online. Choose high-contrast videos with dark backgrounds for best results.";
            case 6:
                return "Test your projector by placing it over your display while playing a hologram video. For smartphone setups, place the phone flat with full brightness and center the pyramid above. Dim the room lighting—holograms work best in low light. Adjust positioning until the image appears clearly from all sides.";
            case 7:
                return "Mount your pyramid on a stable base (cardboard, wood, or 3D-printed material) for a professional finish. Use black or dark colors to minimize reflections. Ensure the base holds the pyramid at the correct height and centered position. Add felt or rubber feet to prevent sliding.";
            case 8:
                return "Fine-tune for best visual effect. Set screen brightness to 70-90%, adjust positioning, and try different viewing angles. If the hologram appears distorted, verify 45° angles and level base. Keep transparent surfaces clean and fingerprint-free. Consider adding a light shield to block ambient light interference.";
            case 9:
                return "Your hologram projector is complete! View from 1-2 feet away in a darkened room to see the 3D illusion floating inside the pyramid. Try different hologram videos—animations, rotating objects, or text. For advanced setups, add motion sensors for interactive displays. Share your creation with friends!";
            default:
                return "Follow this step carefully, ensuring precision throughout. Even small inaccuracies can reduce hologram clarity. Double-check all angles and connections before proceeding. Clean materials and precise assembly are essential for effective results.";
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
     * Generate dynamic tags based on title, index, and hologram type
     * 
     * @param title        The DIY title
     * @param index        The DIY index
     * @param hologramType The hologram type (pyramid, smartphone, led, advanced)
     * @return List of generated tags
     */
    private static List<Tag> generateDynamicTags(String title, int index, String hologramType) {
        List<Tag> tags = new ArrayList<>();
        // Base tags that all hologram projects have
        tags.add(buildTag(TAG_HOLOGRAM, "Hologram"));
        tags.add(buildTag(TAG_DIY, "DIY"));
        tags.add(buildTag(hologramType, StringUtils.capitalize(hologramType) + " Hologram"));

        // Add tags based on title content
        String titleLower = title.toLowerCase();
        if (titleLower.contains("pyramid"))
            tags.add(buildTag("pyramid", "Pyramid Hologram"));
        if (titleLower.contains("smartphone") || titleLower.contains("phone"))
            tags.add(buildTag("smartphone", "Smartphone Compatible"));
        if (titleLower.contains("arduino"))
            tags.add(buildTag("arduino", "Arduino"));
        if (titleLower.contains("led"))
            tags.add(buildTag("led", "LED Lighting"));
        if (titleLower.contains("portable") || titleLower.contains("pocket") || titleLower.contains("travel"))
            tags.add(buildTag("portable", "Portable"));
        if (titleLower.contains("recycled"))
            tags.add(buildTag("eco", "Eco-Friendly"));
        if (titleLower.contains("motion") || titleLower.contains("sensor"))
            tags.add(buildTag("interactive", "Interactive"));
        if (titleLower.contains("3d") || titleLower.contains("three") || titleLower.contains("dimension"))
            tags.add(buildTag("3d", "3D Effect"));

        // Add skill level tags based on index
        if (index % 4 == 0)
            tags.add(buildTag("beginner", "Beginner Friendly"));
        if (index % 4 == 1)
            tags.add(buildTag("intermediate", "Intermediate"));
        if (index % 4 == 2)
            tags.add(buildTag("advanced", "Advanced"));

        // Add educational tag for some projects
        if (index % 3 == 0)
            tags.add(buildTag(TAG_EDUCATIONAL, "Educational"));

        // Add purpose tags based on type
        if (hologramType.equals(TYPE_PYRAMID)) {
            tags.add(buildTag("display", "Visual Display"));
        } else if (hologramType.equals(TYPE_ADVANCED)) {
            tags.add(buildTag("tech", "High-Tech"));
            if (index % 2 == 0)
                tags.add(buildTag("stem", "STEM Project"));
        }
        return tags;
    }

    /**
     * Get a random video URL from the available list
     * 
     * @return A YouTube embed URL for hologram content
     */
    public static String getRandomVideoUrl() {
        return HOLOGRAM_VIDEO_URLS.get(random.nextInt(HOLOGRAM_VIDEO_URLS.size()));
    }

    /**
     * Generate a detailed summary for a hologram projector DIY project
     * 
     * @param title        The project title
     * @param hologramType The hologram type (pyramid, smartphone, led, advanced)
     * @param index        The project index (for variety)
     * @return A detailed summary of the hologram projector DIY project
     */
    private static String generateProjectSummary(String title, String hologramType, int index) {
        String baseTitle = title.toLowerCase()
                .replace("build ", "").replace("create ", "").replace("make ", "");

        // Determine difficulty phrase based on index
        String difficultyPhrase;
        switch (index % 3) {
            case 0:
                difficultyPhrase = "Simple enough for beginners, this project requires only basic materials and tools. Perfect for first-time hologram builders, even kids can follow along with adult supervision.";
                break;
            case 1:
                difficultyPhrase = "Perfect for those with some DIY experience, this intermediate project creates impressive results. With slightly more complex cutting and assembly, you'll create a more refined hologram projector.";
                break;
            default:
                difficultyPhrase = "This advanced hologram projector offers stunning visual quality for those ready for a challenge. Incorporating electronics and precision components results in a professional-grade holographic display.";
        }

        // Get technical details based on hologram type
        String technicalDetails;
        String usagePhrase;

        switch (hologramType) {
            case TYPE_PYRAMID:
                technicalDetails = "This pyramid-style hologram projector uses the principle of Pepper's Ghost - a 19th-century illusion technique modernized for today's displays. ";
                usagePhrase = "Perfect for displaying 3D objects, animations, and visual effects that appear to float in mid-air. Great for education, entertainment, or as a conversation piece.";
                break;
            case TYPE_LED:
                technicalDetails = "By combining programmable electronics with optical principles, this projector creates dynamic holographic effects with customizable lighting. ";
                usagePhrase = "The LED-enhanced design provides brighter, more vivid holographic projections, making them visible even in moderately lit environments. Ideal for demonstrations and interactive displays.";
                break;
            case TYPE_SMARTPHONE:
                technicalDetails = "By leveraging your smartphone's high-resolution display, this projector creates crisp hologram images without expensive equipment. ";
                usagePhrase = "This portable design lets you turn any smartphone into a hologram projector in minutes. Perfect for sharing videos, presentations, or just impressing friends with floating 3D images.";
                break;
            case TYPE_ADVANCED:
                technicalDetails = "Using precision optics and optional electronic components, this advanced projector creates stunning holographic illusions with enhanced clarity and brightness. ";
                usagePhrase = "The sophisticated design allows for larger, more detailed holographic projections with improved visibility from multiple angles. Excellent for exhibits, presentations, or advanced hobbyists.";
                break;
            default:
                technicalDetails = "Using clever optical principles, this hologram projector creates the illusion of floating 3D objects using simple materials. ";
                usagePhrase = "Creates an impressive 3D holographic effect that seems to float in mid-air, turning any 2D content into an immersive visual experience.";
        }

        return "Learn how to build a " + baseTitle + " using readily available materials. "
                + difficultyPhrase + " " + technicalDetails
                + "This hologram projector creates realistic 3D visual effects "
                + "that appear to float in space, using optical illusion principles. "
                + usagePhrase + " Complete step-by-step instructions included with tips for best results.";
    }
}
