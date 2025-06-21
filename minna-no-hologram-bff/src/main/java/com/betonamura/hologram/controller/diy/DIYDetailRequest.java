package com.betonamura.hologram.controller.diy;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DIYDetailRequest {
    private static final String ERR_MSG_SLUGID_REQUIRED = "SlugId is required";
    private static final String ERR_MSG_SLUGID_MAX_LENGTH = "SlugId must be at most 256 characters";

    @NotBlank(message = ERR_MSG_SLUGID_REQUIRED)
    @Size(max = 256, message = ERR_MSG_SLUGID_MAX_LENGTH)
    private String slugId;
}
