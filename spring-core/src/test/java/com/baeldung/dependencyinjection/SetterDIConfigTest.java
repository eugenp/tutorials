package com.baeldung.dependencyinjection;

import com.baeldung.setterdi.domain.Engine;
import com.baeldung.setterdi.domain.Gear;
import com.baeldung.setterdi.domain.MotorCycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.setterdi")
public class SetterDIConfigTest {
    @Bean
    public MotorCycle motorCycle() {
        return new MotorCycle();
    }

    @Bean
    public Engine engine() {
        Engine engine = new Engine();
        engine.setType("v6");
        engine.setVolume(8);
        return engine;
    }

    @Bean
    public Gear gear() {
        Gear gear = new Gear();
        gear.setNumberOfGears("6");
        return gear;
    }
}
