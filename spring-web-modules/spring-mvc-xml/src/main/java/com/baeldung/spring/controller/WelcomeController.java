package com.baeldung.spring.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class WelcomeController extends AbstractController {
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {

        ModelAndView model = new ModelAndView("welcome");
        model.addObject("msg", " Baeldung's Spring Handler Mappings Guide.<br>This request was mapped" + " using SimpleUrlHandlerMapping.");

        return model;
    }
}