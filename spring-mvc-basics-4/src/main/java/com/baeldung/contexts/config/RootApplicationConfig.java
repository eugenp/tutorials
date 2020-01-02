package com.baeldung.contexts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.contexts.Greeting;

@Configuration
@ComponentScan(basePackages = { "com.baeldung.contexts.services" })
public class RootApplicationConfig {

    @Bean
    public Greeting greeting() {
        Greeting greeting = new Greeting();
        greeting.setMessage("Hello World !!");
        return greeting;
    }
}
