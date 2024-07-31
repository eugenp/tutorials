package com.baeldung.controller.circularviewpath;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CircularViewPathController {

    /**
     * A request mapping which may cause circular view path exception
     */
    @GetMapping("/path")
    public String path() {
        return "path";
    }
}
