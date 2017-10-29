package com.baeldung.beaninjectiontypes.constructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.baeldung.beaninjectiontypes.constructor")
public class AppConfig {

    @Bean
    public CPU cpu() {
        return new CPU("Pentium", 1.5);
    }
}
