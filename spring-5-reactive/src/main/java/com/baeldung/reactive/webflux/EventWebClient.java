package com.baeldung.reactive.webflux;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class EventWebClient {

    Logger logger = LoggerFactory.getLogger(EventWebClient.class);

    @Bean
    WebClient getWebClient() {
        return WebClient.create("http://localhost:8081");
    }

    @Bean
    ApplicationRunner demo(WebClient client) {
        return args -> {
            client.get().uri("/events").accept(MediaType.TEXT_EVENT_STREAM).retrieve().bodyToFlux(Event.class).map(s -> String.valueOf(s)).subscribe(msg -> {
                logger.info(msg);
            });
        };
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(EventWebClient.class).properties(java.util.Collections.singletonMap("server.port", "8081")).run(args);
    }

}
