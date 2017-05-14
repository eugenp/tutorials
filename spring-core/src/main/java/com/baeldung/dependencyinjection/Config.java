package com.baeldung.dependencyinjection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.baeldung.dependencyinjection.domain.AirConditioner;
import com.baeldung.dependencyinjection.domain.Building;
import com.baeldung.dependencyinjection.domain.Escalator;
import com.baeldung.dependencyinjection.domain.WaterPump;

@Configuration
@ComponentScan("com.baeldung.dependencyinjection")
public class Config {

    @Bean
    public AirConditioner airConditioner() {

        return new AirConditioner();
    }

    @Bean
    public Escalator escalator() {

        return new Escalator();
    }

    @Bean
    public Building building() {

        return new Building(escalator());
    }

    @Bean(name="waterPump")
    public WaterPump waterPump() {

        return new WaterPump();
    }
}
