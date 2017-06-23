package com.baeldung.injectiontypes.setterbased.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.injectiontypes.setterbased.model.Engine;

@Configuration
@ComponentScan("com.baeldung.injectiontypes.setterbased.model")
public class SetterBasedSpringCarsConfig {

    @Bean
    public String brand() {
        return "Ford";
    }

    @Bean
    public String model() {
        return "Mustang";
    }

    @Bean
    public Engine engine() {
        Engine engine = new Engine();
        engine.setType("v8");
        engine.setVolume(5);
        return engine;
    }
}
