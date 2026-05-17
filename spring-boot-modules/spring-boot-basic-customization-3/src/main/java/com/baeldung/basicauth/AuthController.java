package com.baeldung.basicauth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/extract")
    public ResponseEntity<String> extract(@RequestHeader("Authorization") String authHeader) {
        String[] credentials = BasicAuthExtractor.extractCredentials(authHeader);

        if (credentials != null) {
            String username = credentials[0];
            String password = credentials[1];
            return ResponseEntity.ok("Extracted Username: " + username +
              " Extracted Password: " + password);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}