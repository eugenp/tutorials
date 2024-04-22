package com.baeldung.reactive.oauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.WebClient;

@PropertySource("classpath:default-application.yml")
@SpringBootApplication
public class Spring5ReactiveOauthApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring5ReactiveOauthApplication.class, args);
    }

    @Bean
    public WebClient webClient(ReactiveClientRegistrationRepository clientRegistrationRepo, ServerOAuth2AuthorizedClientRepository authorizedClientRepo) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction filter = new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrationRepo, authorizedClientRepo);
        return WebClient.builder()
            .filter(filter)
            .build();
    }

    @Bean
    public ReactiveClientRegistrationRepository clientRegistrations() {
        ClientRegistration registration =   ClientRegistration.withRegistrationId("bael").authorizationGrantType(
            AuthorizationGrantType.CLIENT_CREDENTIALS).clientId("bael").tokenUri("default").build();

        return new InMemoryReactiveClientRegistrationRepository(registration);

    }
}
