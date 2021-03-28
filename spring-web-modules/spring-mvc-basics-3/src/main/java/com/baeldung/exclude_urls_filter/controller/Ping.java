package com.baeldung.exclude_urls_filter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Ping {

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ResponseEntity<String> pingGet() {
        return new ResponseEntity<String>("pong", HttpStatus.OK);
    }

    @RequestMapping(value = "/health", method = RequestMethod.POST)
    public ResponseEntity<String> pingPost() {
        return new ResponseEntity<String>("pong", HttpStatus.OK);
    }

}
