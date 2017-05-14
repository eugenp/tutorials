package com.baeldung.spring.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public ConstructorApplication initConstructorApplication() {
        return new ConstructorApplication(new FacebookService());
    }

    @Bean
    public SetterApplication initSetterApplication() {
        return new SetterApplication();
    }

    @Bean
    public MessageService initMessageService() {
        return new WhatsappService();
    }
}
