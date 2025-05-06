package com.baeldung.eurekaclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/")
    public String greeting() {
        return String.format("Hello from '%s'!", appName);
    }
}