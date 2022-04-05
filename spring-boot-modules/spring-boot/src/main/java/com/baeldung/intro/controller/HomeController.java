package com.baeldung.intro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String root() {
        return "Index Page";
    }

    @GetMapping("/local")
    public String local() {
        return "/local";
    }
}
