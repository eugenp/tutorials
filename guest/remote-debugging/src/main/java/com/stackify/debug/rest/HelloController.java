package com.stackify.debug.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/hello")
public class HelloController {

    @GetMapping
    public String hello(@RequestParam("name") String name) {
        String message = "Hello, " + name;
        return message;
    }

}
