package com.baeldung.thymeleaf.conditionalclasses;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConditionalClassesController {

    @GetMapping("/conditional-classes")
    public String getConditionalClassesPage( Model model) {
        model.addAttribute("condition", true);
        return "conditionalclasses/conditionalclasses";
    }
}
