package com.baeldung.cookiemanagement.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secured/api")
public class Api {

    @GetMapping("/me")
    public String get(Principal principal) {
        return principal.getName();
    }
}
