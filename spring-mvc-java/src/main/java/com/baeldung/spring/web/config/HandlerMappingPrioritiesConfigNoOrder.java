package com.baeldung.spring.web.config;

import com.baeldung.web.controller.handlermapping.SimpleUrlMappingController;
import com.baeldung.web.controller.handlermapping.BeanNameHandlerMappingController;
import com.baeldung.web.controller.handlermapping.WelcomeController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class HandlerMappingPrioritiesConfigNoOrder {

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        Map<String, Object> urlMap = new HashMap<>();
        urlMap.put("/welcome", test());
        simpleUrlHandlerMapping.setUrlMap(urlMap);
        return simpleUrlHandlerMapping;
    }

    @Bean
    public ControllerClassNameHandlerMapping controllerClassNameHandlerMapping() {
        ControllerClassNameHandlerMapping bean = new ControllerClassNameHandlerMapping();
        return bean;
    }

    @Bean
    BeanNameUrlHandlerMapping beanNameUrlHandlerMapping() {
        BeanNameUrlHandlerMapping beanNameUrlHandlerMapping = new BeanNameUrlHandlerMapping();
        return beanNameUrlHandlerMapping;
    }

    @Bean("/welcome")
    public SimpleUrlMappingController welcomeBaeldungController() {
        return new SimpleUrlMappingController();
    }

    @Bean
    public BeanNameHandlerMappingController test() {
        return new BeanNameHandlerMappingController();
    }

    @Bean
    public WelcomeController welcomeController() {
        return new WelcomeController();
    }
}