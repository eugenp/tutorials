package com.baeldung.docker.app.endpoint;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping
    public String version() {
        return "1.7";
    }
}
