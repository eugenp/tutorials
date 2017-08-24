package com.baeldung.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.baeldung.di")
public class Config {

    @Bean
    public Addition addition() {
        return new Addition();
    }
}
