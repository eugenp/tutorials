package com.baeldung.importannotation.animal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DogConfig {

    @Bean
    Dog dog() {
        return new Dog();
    }
}
