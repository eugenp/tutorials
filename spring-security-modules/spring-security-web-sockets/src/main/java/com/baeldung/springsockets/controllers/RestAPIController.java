package com.baeldung.springsockets.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/rest")
public class RestAPIController {

    @GetMapping(path = "/{name}", produces = "application/json")
    public String getGreeting(@PathVariable("name") String name) {
        return "{\"greeting\" : \"Hello, " + name + "!\"}";
    }

}
