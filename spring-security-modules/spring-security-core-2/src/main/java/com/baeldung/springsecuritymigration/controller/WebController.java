package com.baeldung.springsecuritymigration.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @RequestMapping("/")
    public String home() {
        return "Home Page";
    }

    @RequestMapping("/welcome")
    public String welcome() {
        return "Welcome User";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping("/user-dashboard")
    public String dashboard() {
        return "My Dashboard";
    }
}
