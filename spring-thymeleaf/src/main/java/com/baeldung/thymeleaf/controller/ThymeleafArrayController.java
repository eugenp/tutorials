package com.baeldung.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThymeleafArrayController {
    @GetMapping("/arrays")
    public String arrayController(Model model) {
        String[] continents = new String[7];

        continents[0] = "Africa";
        continents[1] = "Antarctica";
        continents[2] = "Assia";
        continents[3] = "Australia";
        continents[4] = "Europe";
        continents[5] = "North America";
        continents[6] = "Sourth America";

        model.addAttribute("continents", continents);

        return "continents";
    }
}
