package com.betonamura.hologram.controller.video;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.hibernate.validator.constraints.Range;
import org.springframework.util.StringUtils;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class VideosRequest {

    private static final String ERR_MSG_OFFSET_MIN = "Offset must be >= 0";
    private static final String ERR_MSG_LIMIT = "Limit must be between 1 and 50";

    private String categoryId; // comma-separated ids, optional

    @Min(value = 0, message = ERR_MSG_OFFSET_MIN)
    private Integer offset = 0;

    @Range(min = 1, max = 50, message = ERR_MSG_LIMIT)
    private Integer limit = 10;

    public List<String> getCategoryIds() {
        if (!StringUtils.hasText(categoryId))
            return Collections.emptyList();
        return Arrays.asList(categoryId.split(","));
    }
}
