package com.baeldung.web.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/custom")
public class PrintUserController {

    public PrintUserController() {
        super();
    }

    // API

    // print user

    @RequestMapping(value = "/print", method = RequestMethod.GET)
    public void printUser() {
        SecurityContext sc = SecurityContextHolder.getContext();
        System.out.println("Logged User: "+sc.getAuthentication().getName());
    }

}