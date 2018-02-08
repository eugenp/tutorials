package com.baeldung.beansinjectiontypes.constructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.beansinjectiontypes.Car;
import com.baeldung.beansinjectiontypes.Sedan;

@Configuration(value="constructorConfig")
public class Config {
    @Bean
    public Car car() {
        return new Sedan();
    }

    @Bean
    public Dealership dealership() {
        return new Dealership(car());
    }
}
