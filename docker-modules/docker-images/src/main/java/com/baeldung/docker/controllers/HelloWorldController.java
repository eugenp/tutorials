package com.baeldung.docker.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping("/helloworld")
    String helloWorld() {
        return "Hello World!";
    }
}