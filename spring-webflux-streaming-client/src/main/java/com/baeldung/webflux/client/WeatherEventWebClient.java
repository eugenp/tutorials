package com.baeldung.webflux.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.webflux.model.WeatherEvent;

@Configuration
public class WeatherEventWebClient {

    Logger logger = LoggerFactory.getLogger(WeatherEventWebClient.class);

    @Bean
    WebClient client() {
        return WebClient.create("http://localhost:8080/");
    }

    @Bean
    ApplicationRunner runner(WebClient webClient) {
        return args -> webClient.get()
            .uri("weatherstream")
            .retrieve()
            .bodyToFlux(WeatherEvent.class)
            .subscribe(data -> logger.info(data.toString()));
    }
}
