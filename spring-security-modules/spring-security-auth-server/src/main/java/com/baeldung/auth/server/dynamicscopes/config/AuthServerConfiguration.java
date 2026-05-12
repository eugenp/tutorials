package com.baeldung.auth.server.dynamicscopes.config;

import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationContext;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

import java.util.function.Consumer;
import java.util.function.Predicate;

@Configuration
public class AuthServerConfiguration {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AuthServerConfiguration.class);


    @Bean
    @Order(1)
    SecurityFilterChain dynamicScopesAuthorizationServerSecurityFilterChain(HttpSecurity http) {

        http
          .oauth2AuthorizationServer( authServer -> {
              authServer
                .authorizationEndpoint(authorizationEndpoint -> {
                    authorizationEndpoint
                      .authenticationProviders(providers -> {
                          providers.stream()
                            .filter(OAuth2AuthorizationCodeRequestAuthenticationProvider.class::isInstance)
                            .map(p -> (OAuth2AuthorizationCodeRequestAuthenticationProvider)p)
                            .findFirst()
                            .ifPresent(p -> {
                                p.setAuthenticationValidator(dynamicScopesAuthenticationValidator());
                                p.setAuthorizationConsentRequired(dynamicScopesConsentValidator());
                            });
                      } );
                });
          });

        return http.build();
    }

    private Consumer<OAuth2AuthorizationCodeRequestAuthenticationContext> dynamicScopesAuthenticationValidator() {

        return (ctx) -> {
            log.info("Dynamic scopes authentication validator invoked for client: {}, requested scopes: {}",
              ctx.getAuthorizationRequest().getClientId(),
              ctx.getAuthorizationRequest().getScopes());
            // Implement your dynamic scope validation logic here
        };
    }

    private Predicate<OAuth2AuthorizationCodeRequestAuthenticationContext> dynamicScopesConsentValidator() {
        return ctx -> {

            var clientId = ctx.getRegisteredClient().getId();
            var requestId = ctx.getAuthorizationRequest().getAdditionalParameters().get("request_id");
        };
    }

}
