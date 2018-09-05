package com.baeldung.reactive.eventstreaming.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.reactive.eventstreaming.model.Notification;

@Configuration
public class NotificationWebClient {

    Logger logger = LoggerFactory.getLogger(NotificationWebClient.class);

    @Bean
    WebClient client() {
        return WebClient.create("http://localhost:8080");
    }
	
    @Bean
    ApplicationRunner runner(WebClient webClient) {
        return args -> webClient.get()
                .uri("/stream-notifications")
                .retrieve()
                .bodyToFlux(Notification.class)
                .subscribe(data -> logger.info(data.toString()));
    }
}