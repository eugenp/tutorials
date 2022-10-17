package com.baeldung.authresolver;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthResolverController {
    @GetMapping("/customer/welcome")
    public String sayWelcomeToCustomer(Authentication authentication) {
        return String.format("Welcome to our site, %s!", authentication.getPrincipal());
    }

    @GetMapping("/employee/welcome")
    public String sayWelcomeToEmployee(Authentication authentication) {
        return String.format("Welcome to our company, %s!", authentication.getPrincipal());
    }
}
