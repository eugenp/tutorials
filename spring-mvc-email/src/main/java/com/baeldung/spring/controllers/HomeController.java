package com.baeldung.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created with IntelliJ IDEA.
 * User: Olga
 */
@Controller
@RequestMapping({"/","/home"})
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public String showHomePage() {
        return "home";
    }
}
