package org.baeldung.bean.config;

import org.baeldung.bean.injection.Car;
import org.baeldung.bean.injection.Wheel;
import org.springframework.context.annotation.Bean;

public class SetterBasedAppConfig {

    @Bean
    public Car car() {
        return new Car();
    }

    @Bean
    public Wheel wheel() {
        return new Wheel();
    }
}
