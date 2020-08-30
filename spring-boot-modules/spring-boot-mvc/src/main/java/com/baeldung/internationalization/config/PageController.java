package com.baeldung.internationalization.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/international")
    public String getInternationalPage() {
        return "thymeleaf/international";
    }

}
