package com.baeldung.contexts.secure;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.baeldung.contexts.services.ApplicationContextUtilService;
import com.baeldung.contexts.services.GreeterService;

@Controller
public class HelloWorldSecureController {

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private GreeterService greeterService;
    
    @Autowired
    @Qualifier("contextAware")
    private ApplicationContextUtilService contextUtilService; 

    private void processContext() {
        ApplicationContext context = contextUtilService.getApplicationContext();
        System.out.println("application context : " + context);
        System.out.println("application context Beans: " + Arrays.asList(context.getBeanDefinitionNames()));
        
        WebApplicationContext rootContext = ContextLoader.getCurrentWebApplicationContext();
        System.out.println("context : " + rootContext);
        System.out.println("context Beans: " + Arrays.asList(rootContext.getBeanDefinitionNames()));

        System.out.println("context : " + webApplicationContext);
        System.out.println("context Beans: " + Arrays.asList(webApplicationContext.getBeanDefinitionNames()));
    }

    @RequestMapping(path = "/welcome")
    public ModelAndView helloWorld() {
        processContext();
        String message = "<br><div style='text-align:center;'>" + "<h3>Secure " + greeterService.greet() + "</h3></div>";
        return new ModelAndView("welcome", "message", message);
    }
}
