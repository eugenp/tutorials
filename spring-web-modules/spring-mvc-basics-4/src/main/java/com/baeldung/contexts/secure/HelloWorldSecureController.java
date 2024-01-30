package com.baeldung.contexts.secure;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.baeldung.contexts.services.ApplicationContextUtilService;
import com.baeldung.contexts.services.GreeterService;

@Controller
public class HelloWorldSecureController {

    private final Logger logger = LoggerFactory.getLogger(HelloWorldSecureController.class);

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private GreeterService greeterService;

    @Autowired
    @Qualifier("contextAware")
    private ApplicationContextUtilService contextUtilService;

    private void processContext() {
        ApplicationContext context = contextUtilService.getApplicationContext();
        logger.info("application context: {}", context);
        logger.info("application context Beans: {}", Arrays.asList(context.getBeanDefinitionNames()));

        logger.info("context: {}", webApplicationContext);
        logger.info("context Beans: {}", Arrays.asList(webApplicationContext.getBeanDefinitionNames()));
    }

    @GetMapping(path = "/welcome_secure")
    public ModelAndView helloWorld() {
        processContext();
        String message = "<br><div style='text-align:center;'>" + "<h3>Secure " + greeterService.greet() + "</h3></div>";
        return new ModelAndView("/secure/view/welcome", "message", message);
    }
}
