package com.baeldung.application.defaultconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigB {

    @Bean
    public String beanB() {
        return "Bean B";
    }
}
