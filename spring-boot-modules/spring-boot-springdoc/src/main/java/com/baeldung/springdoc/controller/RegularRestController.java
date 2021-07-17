package com.baeldung.springdoc.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;

@Hidden
@RestController
public class RegularRestController {

    @Hidden
    @GetMapping("/getAuthor")
    public String getAuthor() {
        return "Umang Budhwar";
    }

    @GetMapping("/getDate")
    public LocalDate getDate() {
        return LocalDate.now();
    }

    @GetMapping("/getTime")
    public LocalTime getTime() {
        return LocalTime.now();
    }

}
