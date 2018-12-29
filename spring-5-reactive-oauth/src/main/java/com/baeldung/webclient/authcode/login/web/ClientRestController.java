package com.baeldung.webclient.authcode.login.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
public class ClientRestController {

    private static final String RESOURCE_URI = "http://localhost:8084/retrieve-resource";

    @Autowired
    WebClient webClient;

    @GetMapping("/login-oauth")
    Mono<String> useOauthWithAuthCode() {
        Mono<String> retrievedResource = webClient.get()
            .uri(RESOURCE_URI)
            .retrieve()
            .bodyToMono(String.class);
        return retrievedResource.map(string -> "We retrieved the following resource using Oauth: " + string);
    }

}
