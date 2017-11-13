package com.baeldung.ditypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.ditypes")
public class RegisterarConfig {
    @Bean
    public User user() {
        return new User("Alice", 1);
    }
}
