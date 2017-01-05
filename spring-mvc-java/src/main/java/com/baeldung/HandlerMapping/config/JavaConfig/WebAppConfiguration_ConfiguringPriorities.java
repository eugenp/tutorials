package com.baeldung.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.support.ControllerClassNameHandlerMapping;

import com.baeldung.controller.BaeldungController;
import com.baeldung.controller.WelcomeController;
import com.baeldung.controller.TestController;

@Configuration
public class WebAppConfiguration_ConfiguringPriorities {

    @Bean
    BeanNameUrlHandlerMapping beanNameUrlHandlerMapping() {
        BeanNameUrlHandlerMapping beanNameUrlHandlerMapping = new BeanNameUrlHandlerMapping();
        beanNameUrlHandlerMapping.setOrder(2);
        return beanNameUrlHandlerMapping;
    }

    @Bean("/welcome")
    public BaeldungController welcomeBaeldungController() {
        BaeldungController baeldungController = new BaeldungController();
        return baeldungController;
    }

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        Map<String, Object> urlMap = new HashMap<>();
        urlMap.put("/welcome", test());
        simpleUrlHandlerMapping.setUrlMap(urlMap);
        simpleUrlHandlerMapping.setOrder(0);
        return simpleUrlHandlerMapping;
    }

    @Bean
    public TestController test() {
        TestController test = new TestController();
        return test;
    }

    @Bean
    public ControllerClassNameHandlerMapping controllerClassNameHandlerMapping() {
        ControllerClassNameHandlerMapping bean = new ControllerClassNameHandlerMapping();
        bean.setOrder(1);
        return bean;
    }

    @Bean
    public WelcomeController welcomeController() {
        WelcomeController bean = new WelcomeController();
        return bean;
    }
}