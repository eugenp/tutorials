package com.baeldung.cucumber.tags.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @RequestMapping(value="/status", produces= MediaType.APPLICATION_JSON_VALUE)
    public HttpStatus statusCheck() {
        return ResponseEntity.ok().build().getStatusCode();
    }
}
