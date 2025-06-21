package com.betonamura.hologram.controller.like;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import com.betonamura.hologram.config.ApiConfig;

@RestController
public class LikeController {
    @PostMapping(ApiConfig.LIKE)
    public Object like() {
        // TODO: Implement
        return null;
    }
}
