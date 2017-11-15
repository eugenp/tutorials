package com.baeldung.beaninjectiontypes.config;

import com.baeldung.beaninjectiontypes.model.Vehicle;
import com.baeldung.beaninjectiontypes.model.engine.Engine;
import com.baeldung.beaninjectiontypes.model.engine.EngineImplV8;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Engine engineV8() {
        return new EngineImplV8();
    }

    @Bean
    public Vehicle vehicleWithConstructorInjection() {
        return new Vehicle(engineV8());
    }

    @Bean
    public Vehicle vehicleWithSetterInjection() {
        Vehicle vehicle = new Vehicle();
        vehicle.setEngine(engineV8());
        return vehicle;
    }
}