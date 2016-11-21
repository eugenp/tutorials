package com.baeldung.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.spring.domain.Engine;
import com.baeldung.spring.domain.Transmission;

@Configuration
@ComponentScan("com.baeldung.spring")
public class Config {

    @Bean
    public Engine engine() {
        return new Engine("v8", 5);
    }

    @Bean
    public Transmission transmission() {
        return new Transmission("sliding");
    }
}
