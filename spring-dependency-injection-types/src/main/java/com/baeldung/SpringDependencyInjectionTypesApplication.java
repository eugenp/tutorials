package com.baeldung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringDependencyInjectionTypesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDependencyInjectionTypesApplication.class, args);
    }

    @Bean
    public SpeedLimiter speedLimiter() {
        return new SpeedLimiter();
    }

    @Bean
    public Engine engine() {
        return new Engine();
    }

    @Bean
    public Car car() {
        final Car car = new Car(engine());
        car.setSpeedLimiter(speedLimiter());
        return car;
    }
}
