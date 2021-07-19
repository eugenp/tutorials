package com.baeldung.importannotation.animal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BirdConfig {

    @Bean
    Bird bird() {
        return new Bird();
    }
}
