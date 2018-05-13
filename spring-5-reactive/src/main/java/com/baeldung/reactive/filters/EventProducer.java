package com.baeldung.reactive.filters;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.reactive.function.client.WebClient;

@EnableScheduling
@EnableAsync
@Configuration
public class EventProducer {

    private WebClient webClient = WebClient.create("http://localhost:8080");

    @Scheduled(fixedRate = 1000)
    public void produceEvent() {
        webClient.post()
                .uri("/event")
                .syncBody(System.currentTimeMillis())
                .retrieve()
                .bodyToMono(Void.class).block();
    }
}
