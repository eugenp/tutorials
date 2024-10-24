package com.baeldung.dispatchservlet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class Controller {

    @GetMapping
    public String getRequest(){
        return "Baeldung DispatcherServlet";
    }
}
