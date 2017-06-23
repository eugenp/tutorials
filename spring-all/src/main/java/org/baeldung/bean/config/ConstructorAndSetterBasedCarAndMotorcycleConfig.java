package org.baeldung.bean.config;

import org.baeldung.bean.injection.Brake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.baeldung.bean.injection")
public class ConstructorAndSetterBasedCarAndMotorcycleConfig {

    @Bean
    public Brake brake() {
        return new Brake();
    }
}
