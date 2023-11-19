package com.baeldung.controller.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {
    
    @GetMapping(value = "/greetings-with-response-body", produces="application/json")
    public String getGreetingWhileReturnTypeIsString() { 
        return "{\"test\": \"Hello\"}";
    }
        
    @GetMapping(value = "/greetings-with-response-entity", produces = "application/json")
    public ResponseEntity<String> getGreetingWithResponseEntity() {
        final HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>("{\"test\": \"Hello with ResponseEntity\"}", httpHeaders, HttpStatus.OK);
    }
    @GetMapping(value = "/greetings-with-map-return-type", produces = "application/json")
    public Map<String, Object> getGreetingWhileReturnTypeIsMap() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("test", "Hello from map");
        return map;
    }
}