package com.mogronalol;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/")
public class HelloController {

    @GetMapping
    @ResponseBody
    public String getMapping() {
        return "Hello world";
    }

}
