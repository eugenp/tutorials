package com.baeldung.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "public endpoint";
    }

    @GetMapping("/profile")
    public String profileEndpoint() {
        return "profile endpoint";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin dashboard";
    }

}