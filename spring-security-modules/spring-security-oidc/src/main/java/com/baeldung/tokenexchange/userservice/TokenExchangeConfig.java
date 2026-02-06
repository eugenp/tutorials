package com.baeldung.tokenexchange.userservice;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizationContext;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.TokenExchangeOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.util.Assert;

/**
 * @author Steve Riesenberg
 * @since 1.3
 */
@Configuration
public class TokenExchangeConfig {

//    private static final String ACTOR_TOKEN_CLIENT_REGISTRATION_ID = "my-token-exchange-client";

    private static final String IMPERSONATION_CLIENT_REGISTRATION_ID = "my-token-exchange-client";

    /*@Bean public OAuth2AuthorizedClientProvider tokenExchange() {
        return new TokenExchangeOAuth2AuthorizedClientProvider();
    }
    */
    @Bean
    public OAuth2AuthorizedClientProvider tokenExchange(
        ClientRegistrationRepository clientRegistrationRepository,
        OAuth2AuthorizedClientService authorizedClientService) {

        OAuth2AuthorizedClientManager authorizedClientManager = tokenExchangeAuthorizedClientManager(
            clientRegistrationRepository, authorizedClientService);
        Function<OAuth2AuthorizationContext, OAuth2Token> actorTokenResolver = createTokenResolver(
            authorizedClientManager, IMPERSONATION_CLIENT_REGISTRATION_ID);

        TokenExchangeOAuth2AuthorizedClientProvider tokenExchangeAuthorizedClientProvider =
            new TokenExchangeOAuth2AuthorizedClientProvider();
        tokenExchangeAuthorizedClientProvider.setActorTokenResolver(actorTokenResolver);

        return tokenExchangeAuthorizedClientProvider;
    }

    /**
     * Create a standalone {@link OAuth2AuthorizedClientManager} for resolving the actor token
     * using {@code client_credentials}.
     */
    private static OAuth2AuthorizedClientManager tokenExchangeAuthorizedClientManager(
        ClientRegistrationRepository clientRegistrationRepository,
        OAuth2AuthorizedClientService authorizedClientService) {

        // @formatter:off
        OAuth2AuthorizedClientProvider authorizedClientProvider =
            OAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .build();
        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
            new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                clientRegistrationRepository, authorizedClientService);
        // @formatter:on
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }

    /*@Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
        ClientRegistrationRepository clientRegistrationRepository,
        OAuth2AuthorizedClientService authorizedClientService,
        OAuth2AuthorizedClientProvider authorizedClientProvider) {

        AuthorizedClientServiceOAuth2AuthorizedClientManager manager =
            new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                clientRegistrationRepository,
                authorizedClientService
            );

        manager.setAuthorizedClientProvider(authorizedClientProvider);
        return manager;
    }*/

    /**
     * Create a {@code Function} to resolve a token from the current principal.
     */
    private static Function<OAuth2AuthorizationContext, OAuth2Token> createTokenResolver(
        OAuth2AuthorizedClientManager authorizedClientManager, String clientRegistrationId) {

        return (context) -> {
            // Do not provide an actor token for impersonation use case
            if (IMPERSONATION_CLIENT_REGISTRATION_ID.equals(context.getClientRegistration().getRegistrationId())) {
                return null;
            }

            // @formatter:off
            OAuth2AuthorizeRequest authorizeRequest =
                OAuth2AuthorizeRequest.withClientRegistrationId(clientRegistrationId)
                    .principal(context.getPrincipal())
                    .build();
            // @formatter:on

            OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);
            Assert.notNull(authorizedClient, "authorizedClient cannot be null");

            return authorizedClient.getAccessToken();
        };
    }

}