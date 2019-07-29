package com.baeldung.thymeleaf.accessingdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean public String username() {
        return "John";
    }
}
