package com.baeldung;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class ExampleTwoController extends AbstractController
{
    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        System.out.println("Inside ExampleTwo Controller");

        ModelAndView model = new ModelAndView("exampleTwo");
        
        return model;
    }
}