package com.baeldung.custom.deserialization.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ExternalServiceV3 {

    public WebClient.ResponseSpec orderAddress(List<String> address) {

        WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8090/")
            .build();

        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path("/external/order")
                .queryParam("addresses", address.toArray())
                .build())
            .retrieve();
    }

}
