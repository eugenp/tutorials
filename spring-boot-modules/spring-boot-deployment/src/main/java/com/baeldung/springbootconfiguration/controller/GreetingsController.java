package com.baeldung.springbootconfiguration.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingsController {

    @GetMapping("/greetings/{username}")
    public String getGreetings(@PathVariable("username") String userName) {
        return "Hello " + userName + ", Good day...!!!";
    }
}
