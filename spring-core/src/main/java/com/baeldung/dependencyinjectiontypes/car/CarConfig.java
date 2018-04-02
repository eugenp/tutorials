package com.baeldung.dependencyinjectiontypes.car;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.dependencyinjectiontypes.car")
public class CarConfig {

    @Bean
    public Engine engine() {
        return new Engine("V12", 355);
    }
}
