package com.baeldung.custom.deserialization.service;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ExternalServiceV1 {

    private final WebClient.Builder webclientBuilder;

    public ExternalServiceV1(WebClient.Builder webclientBuilder) {
        this.webclientBuilder = webclientBuilder;
    }

    public WebClient.ResponseSpec findById(int id) {
        return webclientBuilder.baseUrl("http://localhost:8090/")
            .build()
            .get()
            .uri("external/order/" + id)
            .retrieve();
    }

}
