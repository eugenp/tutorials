package com.baeldung.webclient.authorizationcodeclient.web;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;
import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
public class ClientRestController {

    private static final String RESOURCE_URI = "http://localhost:8082/spring-security-oauth-resource/foos/1";

    @Autowired
    WebClient webClient;

    @GetMapping("/auth-code")
    Mono<String> useOauthWithAuthCode() {
        Mono<String> retrievedResource = webClient.get()
            .uri(RESOURCE_URI)
            .retrieve()
            .bodyToMono(String.class);
        return retrievedResource.map(string -> "We retrieved the following resource using Oauth: " + string);
    }

    @GetMapping("/auth-code-annotated")
    Mono<String> useOauthWithAuthCodeAndAnnotation(@RegisteredOAuth2AuthorizedClient("bael") OAuth2AuthorizedClient authorizedClient) {
        Mono<String> retrievedResource = webClient.get()
            .uri(RESOURCE_URI)
            .attributes(oauth2AuthorizedClient(authorizedClient))
            .retrieve()
            .bodyToMono(String.class);
        return retrievedResource.map(string -> "We retrieved the following resource using Oauth: " + string + ". Principal associated: " + authorizedClient.getPrincipalName() + ". Token will expire at: " + authorizedClient.getAccessToken()
            .getExpiresAt());
    }

    @GetMapping("/auth-code-explicit-client")
    Mono<String> useOauthWithExpicitClient() {
        Mono<String> retrievedResource = webClient.get()
            .uri(RESOURCE_URI)
            .attributes(clientRegistrationId("bael"))
            .retrieve()
            .bodyToMono(String.class);
        return retrievedResource.map(string -> "We retrieved the following resource using Oauth: " + string);
    }

}
