package com.baeldung.customauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    @GetMapping(path = "/api/hello")
    public String hello(){
        return "hello";
    }
}
