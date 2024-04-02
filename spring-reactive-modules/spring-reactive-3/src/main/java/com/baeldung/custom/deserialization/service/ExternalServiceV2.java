package com.baeldung.custom.deserialization.service;

import java.time.LocalDateTime;

import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.custom.deserialization.config.CustomDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class ExternalServiceV2 {

    public WebClient.ResponseSpec findById(int id) {

        ObjectMapper objectMapper = new ObjectMapper().registerModule(new SimpleModule().addDeserializer(LocalDateTime.class, new CustomDeserializer()));

        WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost:8090/")
            .exchangeStrategies(ExchangeStrategies.builder()
                .codecs(clientDefaultCodecsConfigurer -> {
                    clientDefaultCodecsConfigurer.defaultCodecs()
                        .jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON));
                    clientDefaultCodecsConfigurer.defaultCodecs()
                        .jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON));
                })
                .build())
            .build();

        return webClient.get()
            .uri("external/order/" + id)
            .retrieve();
    }

}
