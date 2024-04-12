package com.baeldung.swagger2boot.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class RegularRestController {

    @ApiIgnore
    @ApiOperation(value = "This method is used to get the author name.")
    @GetMapping("/getAuthor")
    public String getAuthor() {
        return "Umang Budhwar";
    }

    @ApiOperation(value = "This method is used to get the current date.", hidden = true)
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