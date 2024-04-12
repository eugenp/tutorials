package com.baeldung.requesttimeout.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class WebClientConfiguration {

    @Value("${server.port}")
    private int serverPort;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
          .baseUrl("http://localhost:" + serverPort)
          .clientConnector(new ReactorClientHttpConnector(HttpClient.create().responseTimeout(Duration.ofMillis(250))))
          .build();
    }
}
