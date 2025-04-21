package com.baeldung.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, New Relic!";
    }

    @GetMapping("/error")
    public String error() {
        throw new RuntimeException("An error occurred");
    }
}