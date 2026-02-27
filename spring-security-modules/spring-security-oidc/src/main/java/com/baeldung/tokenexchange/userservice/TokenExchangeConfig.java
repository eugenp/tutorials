package com.baeldung.tokenexchange.userservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.TokenExchangeOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.RestClientTokenExchangeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.TokenExchangeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;

@Configuration
public class TokenExchangeConfig {

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
        ClientRegistrationRepository clientRegistrationRepository,
        OAuth2AuthorizedClientRepository authorizedClientRepository) {

        TokenExchangeOAuth2AuthorizedClientProvider tokenExchangeAuthorizedClientProvider =
          new TokenExchangeOAuth2AuthorizedClientProvider();

        OAuth2AuthorizedClientProvider authorizedClientProvider =
          OAuth2AuthorizedClientProviderBuilder.builder()
            .provider(tokenExchangeAuthorizedClientProvider)
            .build();

        DefaultOAuth2AuthorizedClientManager authorizedClientManager =
          new DefaultOAuth2AuthorizedClientManager(
            clientRegistrationRepository, authorizedClientRepository);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }

    @Bean
    public OAuth2AccessTokenResponseClient<TokenExchangeGrantRequest> accessTokenResponseClient() {
        return new RestClientTokenExchangeTokenResponseClient();
    }

}
