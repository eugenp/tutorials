package com.baeldung.injectiontypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.injectiontypes")
public class Config {
    @Bean
    public Hospital hospital() {
        return new Hospital();
    }
}