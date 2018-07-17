package com.baeldung.reactive.webflux.client.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.baeldung.reactive.webflux.model.WebFluxEvent;

import reactor.core.publisher.Flux;

@RequestMapping("/wfclient")
@RestController
public class WebFluxClientController {

    @RequestMapping(value = "/token", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<WebFluxEvent> getToken() {
        WebClient client = WebClient.create("http://localhost:9080");
        return client.get().uri("/wfserver/token")
          .accept(MediaType.APPLICATION_STREAM_JSON).retrieve().bodyToFlux(WebFluxEvent.class).log();
        
    }
}
