package com.baeldung.tutorials.passkey.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/index"})
    public String index(Authentication auth, Model model) {
        model.addAttribute("user", auth.getName());
        return "index";
    }

}
