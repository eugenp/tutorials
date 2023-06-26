package com.baeldung.boot.controller.rest;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class WebController {

    private String name;

    @GetMapping
    public String salutation() {
        return "Hello " + Optional.ofNullable(name).orElse("world") + '!';
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setName(@RequestBody final String name) {
        this.name = name;
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetToDefault() {
        this.name = null;
    }
}