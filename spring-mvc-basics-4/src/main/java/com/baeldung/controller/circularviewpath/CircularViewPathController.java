package com.baeldung.controller.circularviewpath;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CircularViewPathController {

    @RequestMapping("/path")
    public String path() {
        return "path";
    }
}
