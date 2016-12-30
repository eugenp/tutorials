package com.baeldung.web.controller.handlermapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class WelcomeController extends AbstractController
{
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("Inside BeanNameMappingExampleOne Controller");

        ModelAndView model = new ModelAndView("test");
        
        return model;
    }
}