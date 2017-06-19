package com.baeldung.contexts.secure;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.baeldung.contexts.services.GreeterService;

@Controller
public class HelloWorldSecureController {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private GreeterService greeterService;

    private void processContext() {
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        System.out.println("context : " + context);
        System.out.println("context Beans: " + Arrays.asList(context.getBeanDefinitionNames()));

        System.out.println("context : " + webApplicationContext);
        System.out.println("context Beans: " + Arrays.asList(webApplicationContext.getBeanDefinitionNames()));
    }

    @RequestMapping(path = "/welcome")
    public ModelAndView helloWorld() {
        processContext();
        String message = "<br><div style='text-align:center;'>" + "<h3> " + greeterService.greet() + "</h3></div>";
        return new ModelAndView("welcome", "message", message);
    }
}
