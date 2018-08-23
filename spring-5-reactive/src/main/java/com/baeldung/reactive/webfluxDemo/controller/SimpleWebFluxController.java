package com.baeldung.webfluxDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.webfluxDemo.service.SimpleWebFluxService;

import reactor.core.publisher.Flux;

@RestController
public class SimpleWebFluxController {

    @Autowired
    SimpleWebFluxService simpleWebFluxService;

    // Messages are Sent to the client as Server Sent Events
    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public Flux<String> pushEventSignal() {
        return simpleWebFluxService.getInfiniteEventStrings();
    }

}
