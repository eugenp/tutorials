package com.baeldung.thymeleaf.url;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UrlController {
    @GetMapping("/search") public String test(Model model, @RequestParam("query") String query){
        return "url/index";
    }
}
