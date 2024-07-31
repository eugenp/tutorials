package com.baeldung.apikeyauthentication.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {
    @GetMapping("/home")
    public String homeEndpoint() {
        return "Baeldung !";
    }
}
