package com.baeldung.responsebody;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/example")
public class HelloWorldController {

    @GetMapping
    public String getExample() {
        return "Hello, World!";
    }
}