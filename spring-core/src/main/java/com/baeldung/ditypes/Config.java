package com.baeldung.ditypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Config {

    @Bean
    String helloWorldStringBean() {
        return "Hello World!";
    }
}
