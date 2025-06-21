package com.betonamura.hologram.domain.diy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DIYStep {
    private int stepNumber;
    private String title;
    private String imageUrl;
    private String caption;
    private String description;
}
