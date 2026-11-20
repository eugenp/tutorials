package com.baeldung.custom.deserialization.controller;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.custom.deserialization.model.OrderResponse;
import com.baeldung.custom.deserialization.service.ExternalServiceV1;
import com.baeldung.custom.deserialization.service.ExternalServiceV2;
import com.baeldung.custom.deserialization.service.ExternalServiceV3;

import reactor.core.publisher.Mono;

@RestController
public class OrderController {

    private final ExternalServiceV1 externalServiceV1;
    private final ExternalServiceV2 externalServiceV2;
    private final ExternalServiceV3 externalServiceV3;

    public OrderController(ExternalServiceV1 externalServiceV1, ExternalServiceV2 externalServiceV2, ExternalServiceV3 externalServiceV3) {
        this.externalServiceV1 = externalServiceV1;
        this.externalServiceV2 = externalServiceV2;
        this.externalServiceV3 = externalServiceV3;
    }

    @GetMapping(value = "v1/order/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final Mono<OrderResponse> searchOrderV1(@PathVariable(value = "id") int id) {
        return externalServiceV1.findById(id)
            .bodyToMono(OrderResponse.class);
    }

    @GetMapping(value = "v2/order/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public final Mono<OrderResponse> searchOrderV2(@PathVariable(value = "id") int id) {
        return externalServiceV2.findById(id)
            .bodyToMono(OrderResponse.class);
    }

    @GetMapping(value = "v3/order", produces = MediaType.APPLICATION_JSON_VALUE)
    public final Mono<List<String>> searchOrderV3(@RequestParam(value = "address") List<String> address) {
        return externalServiceV3.orderAddress(address)
            .bodyToMono(new ParameterizedTypeReference<List<String>>() {
            })
            .log();
    }

}
