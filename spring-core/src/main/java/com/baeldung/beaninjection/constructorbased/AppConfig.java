package com.baeldung.beaninjection.constructorbased;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ChatMessage chatMessage() {
        return new TextMessage();
    }

    @Bean
    public MessageSender messageSender() {
        return new MessageSender(chatMessage());
    }
}
