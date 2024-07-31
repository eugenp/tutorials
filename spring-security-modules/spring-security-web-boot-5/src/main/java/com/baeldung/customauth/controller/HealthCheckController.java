package com.baeldung.customauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping(path = "/health")
    public String getHealthStatus(){
        return "OK";
    }
}
