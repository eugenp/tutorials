package com.baeldung.messaging.postgresql.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.messaging.postgresql.service.NotifierService;
import com.baeldung.messaging.postgresql.service.NotificationHandler;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class ListenerConfiguration {
    
    @Bean
    CommandLineRunner startListener(NotifierService notifier, NotificationHandler handler) {
        return (args) -> {
            log.info("Starting order listener thread...");            
            Runnable listener = notifier.createNotificationHandler(handler);            
            Thread t = new Thread(listener, "order-listener");
            t.start();
        };
    }
}
