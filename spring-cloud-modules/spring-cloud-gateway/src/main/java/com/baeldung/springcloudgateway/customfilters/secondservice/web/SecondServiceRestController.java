package com.baeldung.springcloudgateway.customfilters.secondservice.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class SecondServiceRestController {

    @GetMapping("/resource/language")
    public Mono<ResponseEntity<String>> getResource() {
        return Mono.just(ResponseEntity.ok()
            .body("es"));

    }
}
