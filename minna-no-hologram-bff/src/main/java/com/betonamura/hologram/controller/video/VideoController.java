package com.betonamura.hologram.controller.video;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.betonamura.hologram.config.ApiConfig;

@RestController
public class VideoController {
    @GetMapping(ApiConfig.VIDEO)
    public Object getVideo(@PathVariable String slugId) {
        // TODO: Implement
        return null;
    }
}
