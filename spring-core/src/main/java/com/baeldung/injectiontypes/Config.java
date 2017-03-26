package com.baeldung.injectiontypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.injectiontypes.domain.Engine;
import com.baeldung.injectiontypes.domain.MultimediaSystem;
import com.baeldung.injectiontypes.domain.TemperatureControlSystem;
import com.baeldung.injectiontypes.domain.Transmission;

@Configuration
@ComponentScan("com.baeldung.injectiontypes")
public class Config {

    @Bean
    public Engine engine() {
        return new Engine("v8", 5);
    }

    @Bean
    public Transmission transmission() {
        return new Transmission("sliding");
    }
    
    @Bean
    public MultimediaSystem multimediaSystem() {
        return new MultimediaSystem("Oomph. Inc");
    }
    
    @Bean
    public TemperatureControlSystem temperatureControlSystem() {
        return new TemperatureControlSystem("Hot sun. Inc");
    }
}
