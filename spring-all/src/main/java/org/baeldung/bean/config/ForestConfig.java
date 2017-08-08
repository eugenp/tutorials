package org.baeldung.bean.config;

import org.baeldung.bean.injection.Animal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ForestConfig {
    @Bean
    public Animal animal() {
        return new Animal();
    }
}
