package com.baeldung.spring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class HelloGuestController extends AbstractController {
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView model = new ModelAndView("helloworld");
        model.addObject("msg", "!! Welcome to baeldung's Spring Handler Mappings Guide. <br> This is using ControllerClassNameHandlerMapping.");

        return model;
    }
}
