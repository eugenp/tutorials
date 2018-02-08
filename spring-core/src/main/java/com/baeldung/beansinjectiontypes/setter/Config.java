package com.baeldung.beansinjectiontypes.setter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.beansinjectiontypes.Car;
import com.baeldung.beansinjectiontypes.Sedan;

@Configuration(value="setterConfig")
public class Config {
    @Bean
    public Car car() {
        return new Sedan();
    }

    @Bean
    public Dealership dealership() {
        Dealership dealership = new Dealership();
        dealership.setCar(car());
        return dealership;
    }
}
