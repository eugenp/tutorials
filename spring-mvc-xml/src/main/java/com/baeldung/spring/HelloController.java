package com.baeldung.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class HelloController extends AbstractController {
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView model = new ModelAndView("helloworld");
        model.addObject("msg", "!! Welcome to baeldung's Spring Handler Mappings Guide. <br> This is using SimpleUrlHandlerMapping.");

        return model;
    }
}
