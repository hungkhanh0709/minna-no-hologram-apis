package com.betonamura.hologram.controller.search;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import com.betonamura.hologram.config.ApiConfig;

@RestController
public class SearchController {
    @GetMapping(ApiConfig.SEARCH)
    public Object search() {
        // TODO: Implement
        return null;
    }
}
