package com.baeldung.overridebean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public Service helloWorld() {
        return new ServiceImpl();
    }
}
