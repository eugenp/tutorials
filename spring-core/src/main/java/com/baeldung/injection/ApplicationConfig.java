package com.baeldung.injection;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

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
