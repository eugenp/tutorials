package org.baeldung.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Web Controller.
 * 
 */
@Controller
public class MyController {

    /**
     * Build the view model for the login page (add authentication error
     * information in the event of an unsuccessful login attempt).
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("message", "Username or password not recognised - please try again.");
        }

        model.setViewName("login");
        return model;

    }

}
