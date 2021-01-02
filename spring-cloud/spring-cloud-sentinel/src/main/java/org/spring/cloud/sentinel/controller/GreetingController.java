package org.spring.cloud.sentinel.controller;

import org.spring.cloud.sentinel.service.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @Autowired
    private GreetingService greetingService;

    @GetMapping("/hello")
    public String getGreeting() {
        return greetingService.getGreeting();
    }

}
