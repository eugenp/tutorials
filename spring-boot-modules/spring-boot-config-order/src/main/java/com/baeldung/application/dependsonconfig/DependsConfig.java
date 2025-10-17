package com.baeldung.application.dependsonconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class DependsConfig {

    @Bean
    public String firstBean() {
        return "FirstBean";
    }

    @Bean
    @DependsOn("firstBean")
    public String secondBean() {
        return "SecondBeanAfterFirst";
    }
}
