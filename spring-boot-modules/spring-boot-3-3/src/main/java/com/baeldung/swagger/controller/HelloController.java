package com.baeldung.swagger.controller;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Operation(summary = "Returns a Hello World message")
    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}