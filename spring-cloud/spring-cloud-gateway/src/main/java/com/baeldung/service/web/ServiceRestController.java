package com.baeldung.service.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceRestController {

    @GetMapping("resource")
    public String getResource() {
        return "Service Resource";
    }
}
