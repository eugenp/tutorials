package com.baeldung.config;

import java.util.HashMap;
import java.util.Map;

import com.baeldung.web.controller.handlermapping.SimpleUrlMappingController;
import com.baeldung.web.controller.handlermapping.BeanNameHandlerMappingController;
import com.baeldung.web.controller.handlermapping.WelcomeController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;


@Configuration
public class HandlerMappingPrioritiesConfig {

    @Bean
    BeanNameUrlHandlerMapping beanNameUrlHandlerMappingOrder1() {
        BeanNameUrlHandlerMapping beanNameUrlHandlerMapping = new BeanNameUrlHandlerMapping();
        beanNameUrlHandlerMapping.setOrder(1);
        return beanNameUrlHandlerMapping;
    }

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMappingOrder0() {
        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        simpleUrlHandlerMapping.setOrder(0);
        Map<String, Object> urlMap = new HashMap<>();
        urlMap.put("/welcome", simpleUrlMapping());
        simpleUrlHandlerMapping.setUrlMap(urlMap);
        return simpleUrlHandlerMapping;
    }

    @Bean
    public SimpleUrlMappingController simpleUrlMapping() {
        return new SimpleUrlMappingController();
    }

    @Bean("/welcome-priorities")
    public BeanNameHandlerMappingController beanNameHandlerMapping() {
        return new BeanNameHandlerMappingController();
    }
}