package com.baeldung.diconst;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.diconst.model.Engine;
import com.baeldung.diconst.model.Tyre;

@Configuration
@ComponentScan("com.baeldung.diconst")
public class SpringConfig {
    @Bean
    public Engine getEngineBean() {
        Engine engine = new Engine("Internal Combustion Engine", 150);
        return engine;
    }
    
    @Bean
    public Tyre getTyreBean() {
        Tyre tyre = new Tyre("Bridgestone", "165/80");
        return tyre;
    }
}