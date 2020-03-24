package com.baeldung.spring.bom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloWorldConfig {

    @Bean
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean();
    }
}
