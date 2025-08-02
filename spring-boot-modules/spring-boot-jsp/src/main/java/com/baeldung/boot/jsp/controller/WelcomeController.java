package com.baeldung.boot.jsp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/course")
public class WelcomeController {

    @GetMapping("/welcome")
    public String greetingAndWelcome() {
        return "course/welcome";
    }

    @GetMapping("/welcome-usebean")
    public String greetingAndWelcomeUseBean() {
        return "course/welcome-usebean";
    }

    @GetMapping("/welcome-by-javabean")
    public String greetingAndWelcomeByJavaBean() {
        return "course/welcome-by-javabean";
    }
}