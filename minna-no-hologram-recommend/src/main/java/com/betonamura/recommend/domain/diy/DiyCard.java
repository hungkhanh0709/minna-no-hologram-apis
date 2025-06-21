package com.betonamura.recommend.domain.diy;

import com.betonamura.recommend.domain.common.ContentCard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * DIY card representation for recommendations.
 * Structure matches the BFF API for consistency.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DiyCard extends ContentCard {
    // Additional fields specific to DIY cards
    private String summary;
    private Integer stepCount;
    private String estimatedTime;
    private String difficulty;
}
