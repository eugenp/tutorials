package com.baeldung.requesttimeout.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
          .baseUrl("http://localhost:8080")
          .clientConnector(new ReactorClientHttpConnector(HttpClient.create().responseTimeout(Duration.ofMillis(250))))
          .build();
    }
}
