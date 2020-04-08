package com.baeldung.application.controller.admin;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    private final Environment environment;

    public AdminController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("isActive")
    public String healthCheck() {
        return "Yes";
    }

    @GetMapping("version")
    public String buildInfo() {
        return environment.getProperty("spring.application.version");
    }
}
