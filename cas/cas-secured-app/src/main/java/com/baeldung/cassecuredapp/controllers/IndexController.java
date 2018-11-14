package com.baeldung.cassecuredapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
