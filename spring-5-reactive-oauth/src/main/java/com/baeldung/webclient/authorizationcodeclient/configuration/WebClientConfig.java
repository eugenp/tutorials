package com.baeldung.webclient.authorizationcodeclient.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    WebClient webClientForAuthorized(ReactiveClientRegistrationRepository clientRegistrations, ServerOAuth2AuthorizedClientRepository authorizedClients) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrations, authorizedClients);
        return WebClient.builder()
            .filter(oauth)
            .build();
    }
}
