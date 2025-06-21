package com.betonamura.recommend.domain.video;

import com.betonamura.recommend.domain.common.ContentCard;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Video card representation for recommendations.
 * Structure matches the BFF API for consistency.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VideoCard extends ContentCard {
    // No additional fields needed, all common fields are in ContentCard
}
