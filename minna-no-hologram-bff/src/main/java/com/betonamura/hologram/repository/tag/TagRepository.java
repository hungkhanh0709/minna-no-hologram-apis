package com.betonamura.hologram.repository.tag;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.betonamura.hologram.domain.tag.Tag;

@Repository
public class TagRepository {
    // Predefined list of tags
    private static final List<Tag> TAGS = List.of(
            Tag.builder().id("hologram").name("Hologram").build(),
            Tag.builder().id("education").name("Education").build(),
            Tag.builder().id("3d").name("3D").build(),
            Tag.builder().id("physics").name("Physics").build(),
            Tag.builder().id("technology").name("Technology").build(),
            Tag.builder().id("science").name("Science").build(),
            Tag.builder().id("history").name("History").build(),
            Tag.builder().id("ancient-history").name("Ancient History").build(),
            Tag.builder().id("archaeology").name("Archaeology").build(),
            Tag.builder().id("artifacts").name("Artifacts").build(),
            Tag.builder().id("culture").name("Culture").build(),
            Tag.builder().id("traditions").name("Traditions").build(),
            Tag.builder().id("arts").name("Arts").build(),
            Tag.builder().id("heritage").name("Heritage").build(),
            Tag.builder().id("diy").name("DIY").build());

    /**
     * Retrieves all tags.
     **/
    public List<Tag> findAll() {
        return TAGS;
    }

    /**
     * Searches for tags based on a query string.
     * 
     * @param query The search query
     * @return A list of tags that match the query
     */
    public List<Tag> search(final String query) {
        if (query == null || query.isBlank())
            return TAGS;
        String lower = query.toLowerCase();
        return TAGS.stream()
                .filter(t -> t.getName().toLowerCase().contains(lower) || t.getId().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    /**
     * Finds a tag by its ID.
     * 
     * @param id The ID of the tag
     * @return The tag if found, otherwise null
     */
    public Tag findById(final String id) {
        if (!StringUtils.hasText(id)) {
            return null;
        }
        // Find the tag by ID, ignoring case
        return TAGS.stream()
                .filter(t -> t.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }
}
