package com.baeldung.beaninjectiontypes.setter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.baeldung.beaninjectiontypes.setter")
public class Config {
    @Bean
    public Type type() {
        return new Type("convertible", 3);
    }
}
