package com.betonamura.hologram.controller.diy;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.betonamura.hologram.config.ApiConfig;

@RestController
public class DIYController {
    @GetMapping(ApiConfig.DIY)
    public Object getDIY(@PathVariable String slugId) {
        // TODO: Implement
        return null;
    }
}
