package com.baeldung.application.defaultconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigA {

    @Bean
    public String beanA() {
        return "Bean A";
    }
}
