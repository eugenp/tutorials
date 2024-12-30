package com.baeldung.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.handlermappings.BeanNameHandlerMappingController;
import com.baeldung.handlermappings.WelcomeController;


@Configuration
public class HandlerMappingDefaultConfig {

    @Bean("/welcome")
    public BeanNameHandlerMappingController beanNameHandlerMapping() {
        return new BeanNameHandlerMappingController();
    }

    @Bean
    public WelcomeController welcomeDefaultMappingConfig() {
        return new WelcomeController();
    }

}