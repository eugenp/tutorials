package com.baeldung.webflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class FluxRouter {

    @Autowired
    private FluxProducer fluxProducer;

    @GetMapping(value = "/produce", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> stockTransactionEvents() {
        return fluxProducer.produceFlux();
    }
}
