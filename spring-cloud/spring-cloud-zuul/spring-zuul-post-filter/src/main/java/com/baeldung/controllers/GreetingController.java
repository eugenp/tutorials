package com.baeldung.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/greeting")
public class GreetingController {

    @GetMapping("/simple")
    public ResponseEntity<String> getSimple() {
        return ResponseEntity.ok("Hi");
    }
}
