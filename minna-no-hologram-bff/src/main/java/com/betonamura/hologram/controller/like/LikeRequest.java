package com.betonamura.hologram.controller.like;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeRequest {
    public static final String TYPE_REQUIRED_MSG = "'type' field is required and must be either 'video' or 'diy'";
    public static final String ID_REQUIRED_MSG = "'id' field is required and must not be blank";

    @NotBlank(message = TYPE_REQUIRED_MSG)
    private String type; // "video" or "diy"

    @NotBlank(message = ID_REQUIRED_MSG)
    private String id;
}
