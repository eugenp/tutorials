package com.baeldung.spring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

public class SimpleControllerHandlerAdapterExampleController extends
AbstractController {

@Override
protected ModelAndView handleRequestInternal(HttpServletRequest arg0,
    HttpServletResponse arg1) throws Exception {
ModelAndView model = new ModelAndView("Greeting");

model.addObject("message", "Dinesh Madhwal");

return model;
}
}