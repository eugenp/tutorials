package com.baeldung.autowiring.controller;

import org.springframework.stereotype.Controller;

import com.baeldung.autowiring.service.MyService;

@Controller
public class FlawedController {
    
    public String control() {
        MyService userService = new MyService();
        return userService.serve();
    }

}
