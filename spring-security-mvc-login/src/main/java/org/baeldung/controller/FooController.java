package org.baeldung.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FooController {

    @GetMapping("/403")
    public String accessDeniedPage(ModelMap model, Principal user) {
        model.addAttribute("user", user.getName());
        return "accessDenied";
    }
}
