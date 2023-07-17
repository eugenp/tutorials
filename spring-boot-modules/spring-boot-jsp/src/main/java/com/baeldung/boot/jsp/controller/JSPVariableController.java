package com.baeldung.boot.jsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/jsp-var")
public class JSPVariableController {

    @GetMapping("/by-jsp")
    public String byJsp() {
        return "/jsp-var/by-jsp";
    }

    @GetMapping("/by-el")
    public String byEl() {
        return "/jsp-var/by-el";
    }

    @GetMapping("/by-jstl")
    public String byJstl() {
        return "/jsp-var/by-jstl";
    }

    @GetMapping("/to-dom")
    public String byDom() {
        return "/jsp-var/to-dom";
    }

}