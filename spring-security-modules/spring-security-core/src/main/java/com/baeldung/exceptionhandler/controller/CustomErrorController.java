package com.baeldung.exceptionhandler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class CustomErrorController {

    @GetMapping("/customError")
    public String customErrorController() {
        return "/error";
    }
}
