package com.baeldung.httpsecurityvswebsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/greeting")
    public String hello() {
        return "Hello Admin";
    }

}
