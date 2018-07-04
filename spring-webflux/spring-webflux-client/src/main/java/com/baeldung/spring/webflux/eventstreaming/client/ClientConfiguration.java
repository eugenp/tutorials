package com.baeldung.spring.webflux.eventstreaming.client;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.spring.webflux.eventstreaming.model.Event;

@Configuration
public class ClientConfiguration {

    @Value("${event.streaming.server-url}")
    private String serverBaseUrl;

    private Consumer<Event> printerConsumer = (System.out::println);

    @Bean
    public WebClient webClient() {
        return WebClient.create(serverBaseUrl);
    }

    @Bean
    CommandLineRunner eventServer(WebClient client) {
        return webVar -> {
            client.get()
                .uri("/events")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .flatMapMany(clientResp -> clientResp.bodyToFlux(Event.class))
                .subscribe(printerConsumer);
        };
    }

}
