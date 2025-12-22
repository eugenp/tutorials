package com.baeldung.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FaultyRestController {
    
    @GetMapping("/exception")
    public ResponseEntity<Void> requestWithException() {
        throw new RuntimeException("Error in the faulty controller!");
    }

}
