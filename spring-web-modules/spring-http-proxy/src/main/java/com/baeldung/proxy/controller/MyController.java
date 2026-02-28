package com.baeldung.proxy.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class MyController {

    @GetMapping("/greet")
    public String greet() {
        return "Hello from the server!";
    }

    @PostMapping("/greet")
    public String greet(@RequestBody String username) {
        return "Hello, " + username + "!";
    }
}