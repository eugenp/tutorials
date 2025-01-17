package com.baeldung.mockjwt.jwtdecoder.junit;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/user")
    public ResponseEntity<String> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok("Hello, " + jwt.getSubject());
    }
}