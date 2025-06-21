package com.betonamura.hologram.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {
    private int page;
    private int pageSize;
    private int totalItems;
    private int totalPages;
}
