package com.baeldung.firstarticle.dependencyinjectiontypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarConfig {

    @Bean
    public static Spoiler spoiler() {
        return new Spoiler();
    }

    @Bean
    public static RegularCar regularCar() {
        RegularCar regularCar = new RegularCar();
        regularCar.setSpoiler(spoiler());
        return regularCar;
    }

    @Bean
    public static SportCar sportCar(Spoiler spoiler) {
        return new SportCar(spoiler);
    }

}
