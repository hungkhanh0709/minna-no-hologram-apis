package com.betonamura.recommend.domain.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Tag model for categorizing content.
 * Structure matches the BFF API for consistency.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    private String id;
    private String name;
}
