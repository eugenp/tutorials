package com.baeldung.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan(basePackages="com.baeldung.primary")
public class CarConfig {

    @Bean(name = "mercedesCar")
    public Car car1() {
        return new Car("Mercedes");
    }

    @Bean(name = "bmwCar")
    @Primary
    public Car car2() {
        return new Car("BMW");
    }

}
