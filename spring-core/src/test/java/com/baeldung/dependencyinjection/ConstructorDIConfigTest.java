package com.baeldung.dependencyinjection;

import com.baeldung.constructordi.domain.Engine;
import com.baeldung.constructordi.domain.Gear;
import com.baeldung.constructordi.domain.MotorCycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.constructordi")
public class ConstructorDIConfigTest {

    @Bean
    public MotorCycle motorCycle() {
        return new MotorCycle(engine(), gear());
    }

    @Bean
    public Engine engine() {
        Engine engine = new Engine("v6", 8);
        return engine;
    }

    @Bean
    public Gear gear() {
        Gear gear = new Gear("6");
        return gear;
    }
}
