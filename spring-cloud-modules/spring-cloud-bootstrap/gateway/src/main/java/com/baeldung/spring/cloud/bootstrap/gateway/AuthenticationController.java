package com.baeldung.spring.cloud.bootstrap.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class AuthenticationController {
    @GetMapping("/me")
    public Principal getMyUser(Principal principal) {
        return principal;
    }
}
