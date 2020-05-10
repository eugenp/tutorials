package com.baeldung.auth0.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.auth0.SessionUtils;

@Controller
public class HomeController {
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String home(HttpServletRequest request, HttpServletResponse response) {
        return "Welcome, " + SessionUtils.get(request, "email") + "!";
    }

}
