package com.baeldung.egoebelbecker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DogConfiguration {

    @Bean
    public static Pet injectDog() {
        return new Dog();
    }

}
