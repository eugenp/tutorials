package com.baeldung.security.azuread.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/")
@Slf4j
public class IndexController {

    @GetMapping
    public String index(Model model, Authentication user) {
        log.info("GET /: user={}", user);
        model.addAttribute("user", user);
        return "index";
    }
}
