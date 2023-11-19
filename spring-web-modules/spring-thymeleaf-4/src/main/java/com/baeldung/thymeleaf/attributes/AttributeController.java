package com.baeldung.thymeleaf.attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/attributes")
public class AttributeController {

    private static final Logger logger = LoggerFactory.getLogger(AttributeController.class);

    @GetMapping
    public String show(Model model) {
        model.addAttribute("title", "Baeldung");
        model.addAttribute("email", "default@example.com");
        return "attributes/index";
    }

    @PostMapping
    public String submit(String email) {
        logger.info("Email: {}", email);
        return "redirect:attributes";
    }

}
