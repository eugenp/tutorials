package com.baeldung.autowiring.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyServiceConfiguration {

    @Bean
    MyService myService() {
        return new MyService();
    }
    
}
