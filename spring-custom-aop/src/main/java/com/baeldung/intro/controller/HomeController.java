package com.baeldung.intro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/")
    public String root() {
        return "Index Page";
    }

    @RequestMapping("/local")
    public String local() {
        return "/local";
    }
}
