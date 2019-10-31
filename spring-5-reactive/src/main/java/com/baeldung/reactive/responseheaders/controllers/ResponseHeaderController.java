package com.baeldung.reactive.responseheaders.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/response-header")
public class ResponseHeaderController {

    @GetMapping("/response-entity")
    public Mono<ResponseEntity<String>> usingResponseEntityBuilder() {
        String responseHeaderKey = "Baeldung-Example-Header";
        String responseHeaderValue = "Value-ResponseEntityBuilder";
        String responseBody = "Response with header using ResponseEntity (builder)";

        return Mono.just(ResponseEntity.ok()
            .header(responseHeaderKey, responseHeaderValue)
            .body(responseBody));
    }
    
    @GetMapping("/server-http-response")
    public Mono<String> usingServerHttpResponse(ServerHttpResponse response) {
        String responseHeaderKey = "Baeldung-Example-Header";
        String responseHeaderValue = "Value-ServerHttpResponse";
        String responseBody = "Response with header using ServerHttpResponse";
        
        response.getHeaders().add(responseHeaderKey, responseHeaderValue);
        return Mono.just(responseBody);
    }
}
