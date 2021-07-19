package com.baeldung.importannotation.animal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CatConfig {

    @Bean
    Cat cat() {
        return new Cat();
    }
}
