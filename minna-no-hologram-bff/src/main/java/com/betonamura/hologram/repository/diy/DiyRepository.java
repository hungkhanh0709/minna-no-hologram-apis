package com.betonamura.hologram.repository.diy;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.betonamura.hologram.domain.diy.DIYStep;
import com.betonamura.hologram.domain.diy.DiyCard;
import com.betonamura.hologram.domain.diy.DiyDetail;
import com.betonamura.hologram.domain.diy.Material;
import com.betonamura.hologram.domain.tag.Tag;

@Repository
public class DiyRepository {

    public List<DiyCard> search(final int offset, final int limit) {
        return search(offset, limit, null);
    }

    /**
     * Searches for DIY projects based on the provided keyword, offset, and limit.
     * 
     * @param offset  The starting index for pagination.
     * @param limit   The maximum number of results to return.
     * @param keyword The search keyword (not used in this dummy implementation).
     * @return A list of DIY projects matching the search criteria.
     */
    public List<DiyCard> search(final int offset, final int limit, final String query) {
        // Tag
        final Tag makerTag = Tag.builder().id("maker").name("Maker").build();
        final List<Tag> tags = List.of(makerTag);

        // Return 1 dummy DIY for home page, support offset/limit
        final DiyCard diy = DiyCard.builder()
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
        List<DiyCard> all = List.of(diy);
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

    /**
     * Retrieves the details of a DIY article detail by its slug ID.
     * 
     * @param slugId The slug ID of the DIY project.
     * @return A DiyDetail object containing the details of the DIY project.
     */
    public DiyDetail getDiyDetail(final String slugId) {
        if (!"build-pyramid-hologram-projector".equals(slugId)) {
            return null;
        }
        return DiyDetail.builder()
                .id("456")
                .slug("build-pyramid-hologram-projector")
                .title("Build Your Own Pyramid Hologram Projector")
                .videoUrl("https://example.com/videos/pyramid-tutorial.mp4")
                .summary("Learn how to build a simple pyramid hologram projector using household materials.")
                .stepCount(5)
                .estimatedTime("10-15 min")
                .difficulty("easy")
                .tags(List.of(Tag.builder().id("maker").name("Maker").build()))
                .likeCount(750)
                .materials(List.of(
                        new Material("Clear plastic sheet", false),
                        new Material("Scissors", false),
                        new Material("Ruler", false),
                        new Material("Marker", false),
                        new Material("Tape", true)))
                .steps(List.of(
                        DIYStep.builder()
                                .stepNumber(1)
                                .title("Gather Materials")
                                .imageUrl("https://example.com/images/diy/step1.jpg")
                                .caption("Materials needed for the hologram projector")
                                .description(
                                        "Start by gathering all the materials you'll need: clear plastic sheet, scissors, ruler, and marker.")
                                .build(),
                        DIYStep.builder()
                                .stepNumber(2)
                                .title("Cut the Plastic Sheet")
                                .imageUrl("https://example.com/images/diy/step2.jpg")
                                .caption("Cut the plastic into trapezoids")
                                .description(
                                        "Use the ruler and marker to draw four identical trapezoids on the plastic sheet, then cut them out with scissors.")
                                .build(),
                        DIYStep.builder()
                                .stepNumber(3)
                                .title("Assemble the Pyramid")
                                .imageUrl("https://example.com/images/diy/step3.jpg")
                                .caption("Tape the trapezoids together")
                                .description("Tape the sides of the trapezoids together to form a pyramid shape.")
                                .build(),
                        DIYStep.builder()
                                .stepNumber(4)
                                .title("Place on Device")
                                .imageUrl("https://example.com/images/diy/step4.jpg")
                                .caption("Position the pyramid on your phone or tablet")
                                .description("Place the pyramid upside down at the center of your device's screen.")
                                .build(),
                        DIYStep.builder()
                                .stepNumber(5)
                                .title("Play Hologram Video")
                                .imageUrl("https://example.com/images/diy/step5.jpg")
                                .caption("Enjoy the hologram effect!")
                                .description("Play a hologram video and watch the 3D effect appear inside the pyramid.")
                                .build()))
                .relatedDIY(List.of(
                        DiyCard.builder()
                                .id("789")
                                .slug("make-hologram-fan")
                                .title("Make a Hologram Fan Display")
                                .thumbnail("https://example.com/thumbnails/hologram-fan.jpg")
                                .summary("Create a spinning fan that projects 3D holograms in mid-air.")
                                .stepCount(7)
                                .estimatedTime("20-30 min")
                                .difficulty("medium")
                                .tags(List.of(Tag.builder().id("maker").name("Maker").build()))
                                .likeCount(320)
                                .build()))
                .createdAt(java.time.OffsetDateTime.parse("2023-06-20T14:45:00Z"))
                .build();
    }
}
