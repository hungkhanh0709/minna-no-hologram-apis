package com.betonamura.hologram.controller.diy;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class DIYsRequest {
    private static final String ERR_MSG_OFFSET_MIN = "Offset must be >= 0";
    private static final String ERR_MSG_LIMIT = "Limit must be between 1 and 50";

    @Min(value = 0, message = ERR_MSG_OFFSET_MIN)
    private Integer offset = 0;

    @Range(min = 1, max = 50, message = ERR_MSG_LIMIT)
    private Integer limit = 10;
}
