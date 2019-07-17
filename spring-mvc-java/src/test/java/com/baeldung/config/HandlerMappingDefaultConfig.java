package com.baeldung.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.web.controller.handlermapping.BeanNameHandlerMappingController;
import com.baeldung.web.controller.handlermapping.WelcomeController;


@Configuration
public class HandlerMappingDefaultConfig {

    @Bean("/welcome")
    public BeanNameHandlerMappingController beanNameHandlerMapping() {
        return new BeanNameHandlerMappingController();
    }

    @Bean
    public WelcomeController welcome() {
        return new WelcomeController();
    }

}