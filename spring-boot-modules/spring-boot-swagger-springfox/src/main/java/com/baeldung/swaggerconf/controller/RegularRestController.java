package com.baeldung.swaggerconf.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;

@RestController
@RequestMapping("good-path")
public class RegularRestController {

    @ApiOperation(value = "This method is used to get the author name.")
    @GetMapping("/getAuthor")
    public String getAuthor() {
        return "Name Surname";
    }

    @ApiOperation(value = "This method is used to get the current date.")
    @GetMapping("/getDate")
    public LocalDate getDate() {
        return LocalDate.now();
    }

    @ApiOperation(value = "This method is used to get the current time.")
    @GetMapping("/getTime")
    public LocalTime getTime() {
        return LocalTime.now();
    }

}