package com.baeldung.errorhandling.boot.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FaultyRestController {
    
    @GetMapping("/exception")
    public ResponseEntity<Void> requestWithException() {
        throw new NullPointerException("Error in the faulty controller!");
    }

}
