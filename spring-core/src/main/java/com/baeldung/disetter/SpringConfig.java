package com.baeldung.disetter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.disetter.model.Engine;
import com.baeldung.disetter.model.Tyre;

@Configuration
@ComponentScan("com.baeldung.disetter")
public class SpringConfig {

    @Bean
    public Engine getEngineBean() {
        Engine engine = new Engine();
        engine.setType("Internal Combustion Engine");
        engine.setDisplacement(150);
        return engine;
    }
    
    @Bean
    public Tyre getTyreBean() {
        Tyre tyre = new Tyre();
        tyre.setBrand("Bridgestone");
        tyre.setSize("165/81");
        return tyre;
    }
}