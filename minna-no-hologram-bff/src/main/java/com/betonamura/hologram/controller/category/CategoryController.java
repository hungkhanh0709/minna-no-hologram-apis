package com.betonamura.hologram.controller.category;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import com.betonamura.hologram.config.ApiConfig;

@RestController
public class CategoryController {
    @GetMapping(ApiConfig.CATEGORIES)
    public Object getCategories() {
        // TODO: Implement
        return null;
    }
}
