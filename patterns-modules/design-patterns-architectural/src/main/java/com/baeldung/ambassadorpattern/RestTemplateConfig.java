package com.baeldung.ambassadorpattern;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private final int connectTimeoutSeconds;
    private final int readTimeoutSeconds;
    private final RestTemplateBuilder restTemplateBuilder;

    public RestTemplateConfig(RestTemplateBuilder restTemplateBuilder, @Value("${http.client.read-timeout-seconds}") int readTimeoutSeconds,
        @Value("${http.client.connect-timeout-seconds}") int connectTimeoutSeconds) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.readTimeoutSeconds = readTimeoutSeconds;
        this.connectTimeoutSeconds = connectTimeoutSeconds;
    }

    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder.setConnectTimeout(Duration.ofMillis(connectTimeoutSeconds))
            .setReadTimeout(Duration.ofMillis(readTimeoutSeconds))
            .build();
    }
}
