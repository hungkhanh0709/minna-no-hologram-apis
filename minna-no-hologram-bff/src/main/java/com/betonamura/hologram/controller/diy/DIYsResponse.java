package com.betonamura.hologram.controller.diy;

import java.util.List;

import com.betonamura.hologram.controller.Pagination;
import com.betonamura.hologram.domain.diy.DiyCard;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DIYsResponse {
    private List<DiyCard> results;
    private Pagination pagination;
}
