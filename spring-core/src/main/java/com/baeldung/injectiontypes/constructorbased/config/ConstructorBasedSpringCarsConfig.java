package com.baeldung.injectiontypes.constructorbased.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.injectiontypes.constructorbased.model.Engine;

@Configuration
@ComponentScan("com.baeldung.injectiontypes.constructorbased.model")
public class ConstructorBasedSpringCarsConfig {

    @Bean
    public String brand() {
        return "Honda";
    }

    @Bean
    public String model() {
        return "Civic";
    }

    @Bean
    public Engine engine() {
        Engine engine = new Engine();
        engine.setType("v4");
        engine.setVolume(2);
        return engine;
    }
}
