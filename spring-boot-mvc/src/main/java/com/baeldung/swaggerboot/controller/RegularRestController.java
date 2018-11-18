package com.baeldung.swaggerboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegularRestController {

    @GetMapping("home")
    public String getSession() {
        return "Hello";
    }

}