package com.betonamura.hologram.repository.category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.betonamura.hologram.domain.category.Category;

@Repository
public class CategoryRepository {
    private static final List<Category> CATEGORIES = List.of(
            Category.builder().id("science").name("Science")
                    .description("Scientific topics, experiments, and discoveries").build(),
            Category.builder().id("history").name("History")
                    .description("Historical events, figures, and archaeological discoveries").build(),
            Category.builder().id("culture").name("Culture")
                    .description("Cultural practices, arts, traditions, and heritage").build());

    /**
     * Retrieves all categories.
     */
    public List<Category> findAll() {
        return CATEGORIES;
    }

    /**
     * Searches for categories based on a query string.
     * 
     * @param query The search query
     * @return A list of categories that match the query
     */
    public List<Category> search(final String query) {
        if (!StringUtils.hasText(query)) {
            return CATEGORIES;
        }

        final String lower = query.toLowerCase();
        return CATEGORIES.stream()
                .filter(c -> c.getName().toLowerCase().contains(lower)
                        || c.getDescription().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    /**
     * Finds a category by its ID.
     * 
     * @param id The ID of the category
     * @return The category if found, otherwise null
     */
    public Category findById(final String id) {
        if (!StringUtils.hasText(id)) {
            return null;
        }
        return CATEGORIES.stream()
                .filter(c -> c.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }
}
