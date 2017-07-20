package com.baeldung.egoebelbecker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PetConfiguration {

    @Bean
    public Pet injectCat() {
        return new Cat();
    }

    @Bean
    public Pet injectDog() {
        return new Dog();
    }

}
