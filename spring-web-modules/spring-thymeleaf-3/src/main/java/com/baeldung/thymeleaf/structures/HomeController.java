package com.baeldung.thymeleaf.structures;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {

    @GetMapping("/home-page")
    public String homePage(Model model) {
        String title = "Introduction to Java";
        String subtitle = "[Java powers over 1 billion devices]";

        model.addAttribute("title", title);
        model.addAttribute("subtitle", subtitle);

        return "structures/home";
    }

}
