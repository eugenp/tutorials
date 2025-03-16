package com.baeldung.mockjwt.jwtdecoder.junit;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/user")
    public ResponseEntity<String> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null || jwt.getSubject() == null) {
            throw new JwtValidationException("Invalid token", Arrays.asList(new OAuth2Error("invalid_token")));
        }

        Instant expiration = jwt.getExpiresAt();
        if (expiration != null && expiration.isBefore(Instant.now())) {
            throw new JwtValidationException("Token has expired", Arrays.asList(new OAuth2Error("expired_token")));
        }

        return ResponseEntity.ok("Hello, " + jwt.getSubject());
    }
}