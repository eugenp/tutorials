package com.baeldung.beaninjection.setterbased;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.beaninjection.ChatMessage;
import com.baeldung.beaninjection.TextMessage;

@Configuration
public class AppConfig {

    @Bean
    public ChatMessage chatMessage() {
        return new TextMessage();
    }

    @Bean
    public MessageSender messageSender() {
        MessageSender messageSender = new MessageSender();
        messageSender.setMessage(chatMessage());
        return messageSender;
    }
}
