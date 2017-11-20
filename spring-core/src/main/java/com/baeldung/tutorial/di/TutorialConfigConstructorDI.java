package com.baeldung.tutorial.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TutorialConfigConstructorDI {
    
    @Bean(name = "message")
    public String message() {
        return "Welcome to Baeldung!. Constructor DI Java";
    }

    @Bean(name = "printService")
    public PrintService printService() {
        return new PrintService(message());
    }
}