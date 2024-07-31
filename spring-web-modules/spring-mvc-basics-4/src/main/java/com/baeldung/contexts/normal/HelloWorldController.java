package com.baeldung.contexts.normal;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.baeldung.contexts.services.GreeterService;

@Controller
public class HelloWorldController {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private GreeterService greeterService;

    @Autowired
    private ApplicationContext applicationContext;
    
    private void processContext() {
        System.out.println("root context : " + applicationContext);
        System.out.println("root context Beans: " + Arrays.asList(applicationContext.getBeanDefinitionNames()));

        System.out.println("context : " + webApplicationContext);
        System.out.println("context Beans: " + Arrays.asList(webApplicationContext.getBeanDefinitionNames()));
    }

    @GetMapping(path = "/welcome")
    public ModelAndView helloWorld() {
        processContext();
        String message = "<br><div style='text-align:center;'>" + "<h3>Normal " + greeterService.greet() + "</h3></div>";
        return new ModelAndView("/view/welcome", "message", message);
    }
}
