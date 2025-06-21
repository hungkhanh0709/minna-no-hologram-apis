package com.betonamura.hologram.controller.search;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SearchRequest {
    private static final String ERR_MSG_KEYWORD_REQUIRED = "Search keyword is required";
    private static final String ERR_MSG_OFFSET_MIN = "Offset must be >= 0";
    private static final String ERR_MSG_LIMIT = "Limit must be between 1 and 50";

    @NotBlank(message = ERR_MSG_KEYWORD_REQUIRED)
    private String p;

    @Min(value = 0, message = ERR_MSG_OFFSET_MIN)
    private Integer offset = 0;

    @Range(min = 1, max = 50, message = ERR_MSG_LIMIT)
    private Integer limit = 10;
}
