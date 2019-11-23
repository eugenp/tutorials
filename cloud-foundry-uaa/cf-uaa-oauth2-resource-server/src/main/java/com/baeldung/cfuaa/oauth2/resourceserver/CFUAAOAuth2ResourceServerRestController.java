package com.baeldung.cfuaa.oauth2.resourceserver;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class CFUAAOAuth2ResourceServerRestController {

    @GetMapping("/")
    public String index(@AuthenticationPrincipal Jwt jwt) {
        return String.format("Hello, %s!", jwt.getSubject());
    }

    @GetMapping("/read")
    public String read(JwtAuthenticationToken jwtAuthenticationToken) {
        return "Hello read: " + jwtAuthenticationToken.getTokenAttributes();
    }

    @GetMapping("/write")
    public String write(Principal principal) {
        return "Hello write: " + principal.getName();
    }
}
