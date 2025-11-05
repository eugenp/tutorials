package com.baeldung.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class SampleController {

    @RequestMapping("hello")
    public String sayHello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello, %s!", name);
    }

    @GetMapping("welcome")
    public String getStudent(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Welcome, %s!", name);
    }
}
