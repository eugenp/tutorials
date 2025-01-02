package com.baeldung.spring.security.dynreg.client.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping(path = {"/", "/index"} )
    String index( Authentication user, Model model) {
        model.addAttribute("user", user);
        return "index";
    }
}
