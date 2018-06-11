package com.baeldung.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.services.IHomeService;

@RestController
public class GreetingController {

    @Autowired
    IHomeService homeService;

    @GetMapping(value = "/greeting", produces = "application/json")
    public String getGreeting() {
        return homeService.getGreeting();
    }
}
