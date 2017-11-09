package com.baeldung.ditypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.constructordi.domain.Engine;
import com.baeldung.constructordi.domain.Transmission;

@Configuration
@ComponentScan("com.baeldung.ditypes")
public class Config {

    @Bean
    public Engine engine() {
        return new Engine("v8", 5);
    }

    @Bean
    public Transmission transmission() {
        return new Transmission("automatic");
    }
}
