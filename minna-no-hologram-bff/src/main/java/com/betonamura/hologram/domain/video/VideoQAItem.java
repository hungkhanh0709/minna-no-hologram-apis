package com.betonamura.hologram.domain.video;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoQAItem {
    private String question;
    private String answer;
    private String imageUrl;

    // Constructor without imageUrl for backward compatibility
    public VideoQAItem(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.imageUrl = null;
    }
}
