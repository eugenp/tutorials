package com.baeldung.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping;

import com.baeldung.controller.WelcomeBaeldungController;
import com.baeldung.controller.WelcomeController;
import com.baeldung.controller.WelcomeTwoController;

@Configuration
public class WebAppConfiguration_ConfiguringPriorities {

    @Bean
    BeanNameUrlHandlerMapping beanNameUrlHandlerMapping() {
        BeanNameUrlHandlerMapping beanNameUrlHandlerMapping = new BeanNameUrlHandlerMapping();
        beanNameUrlHandlerMapping.setOrder(2);
        return beanNameUrlHandlerMapping;
    }

    @Bean("/welcome")
    public WelcomeBaeldungController welcomeBaeldungController() {
        WelcomeBaeldungController welcomeBaeldungController = new WelcomeBaeldungController();
        return welcomeBaeldungController;
    }

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        Map<String, Object> urlMap = new HashMap<>();
        urlMap.put("/welcome", welcome());
        simpleUrlHandlerMapping.setUrlMap(urlMap);
        simpleUrlHandlerMapping.setOrder(0);
        return simpleUrlHandlerMapping;
    }

    @Bean
    public WelcomeTwoController welcome() {
        WelcomeTwoController bean = new WelcomeTwoController();
        return bean;
    }

    @Bean
    public ControllerClassNameHandlerMapping controllerClassNameHandlerMapping() {
        ControllerClassNameHandlerMapping controllerClassNameHandlerMapping = new ControllerClassNameHandlerMapping();
        controllerClassNameHandlerMapping.setOrder(1);
        return controllerClassNameHandlerMapping;
    }

    @Bean
    public WelcomeController welcomeController() {
        WelcomeController bean = new WelcomeController();
        return bean;
    }
}