package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/config")
public class DemoController {
    // We're not using JDBC, but this is the example property from the article.
    @Value("${spring.datasource.url}")
    private String someValue;

    @GetMapping
    public String get() {
        return "Hello, " + someValue;
    }
}

