package com.baeldung.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created with IntelliJ IDEA.
 * User: Olga
 */
@RestController
@RequestMapping({"/","/home"})
public class HomeController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showHomePage() {
        return new ModelAndView("home");
    }
}
