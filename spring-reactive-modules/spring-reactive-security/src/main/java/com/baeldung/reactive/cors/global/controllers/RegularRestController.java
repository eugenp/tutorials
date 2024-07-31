package com.baeldung.reactive.cors.global.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController("RegularRestController-cors-on-global-config")
@RequestMapping("/cors-on-global-config")
public class RegularRestController {

    @PutMapping("/regular-put-endpoint")
    public Mono<String> regularPutEndpoint() {
        return Mono.just("Regular PUT endpoint");
    }

    @DeleteMapping("/regular-delete-endpoint")
    public Mono<String> regularDeleteEndpoint() {
        return Mono.just("Regular DELETE endpoint");
    }
}
