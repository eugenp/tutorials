package org.baeldung.bean.config;

import org.baeldung.bean.injection.Car;
import org.baeldung.bean.injection.Wheel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConstructorBasedAppConfig {

    @Bean
    public Wheel wheel() {
        return new Wheel();
    }
    
	@Bean
    public Car car() {
        return new Car(wheel());
    }

}
