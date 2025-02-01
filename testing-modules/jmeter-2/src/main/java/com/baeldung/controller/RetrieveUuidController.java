package com.baeldung.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static java.lang.String.format;

@RestController
public class RetrieveUuidController {

    @GetMapping("/api/uuid")
    public ResponseEntity<String> uuid() {
        return ResponseEntity.ok(format("Test message... %s.", UUID.randomUUID()));
    }
}
