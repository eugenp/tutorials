package com.baeldung.servlets3.spring.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uppercase")
public class UppercaseController {

    @GetMapping(produces = "text/html")
    public String getUppercase(@RequestParam(required = false)String param) {
        String response = param != null ? param.toUpperCase() : "Missing param";
        return "From Spring: " + response;
    }
}
