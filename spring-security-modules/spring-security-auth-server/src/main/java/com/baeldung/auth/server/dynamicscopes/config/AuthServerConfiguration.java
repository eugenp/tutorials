package com.baeldung.auth.server.dynamicscopes.config;

import com.baeldung.auth.server.dynamicscopes.components.DynamicScopeService;
import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationContext;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationException;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Configuration
public class AuthServerConfiguration {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AuthServerConfiguration.class);

    private final DynamicScopeService dynamicScopeService;

    public AuthServerConfiguration(DynamicScopeService dynamicScopeService) {
        this.dynamicScopeService = dynamicScopeService;
    }

    @Bean
    @Order(1)
    SecurityFilterChain dynamicScopesAuthorizationServerSecurityFilterChain(HttpSecurity http) {

        http.oauth2AuthorizationServer( authServer -> {
            http.securityMatcher(authServer.getEndpointsMatcher());

            authServer
              .oidc(Customizer.withDefaults()) // Enable OpenID Connect 1.0
              .authorizationEndpoint(authorizationEndpoint -> authorizationEndpoint
                .authenticationProviders(providers -> providers.stream()
                  .filter(OAuth2AuthorizationCodeRequestAuthenticationProvider.class::isInstance)
                  .map(p -> (OAuth2AuthorizationCodeRequestAuthenticationProvider)p)
                  .findFirst()
                  .ifPresent(p -> {
                    p.setAuthenticationValidator(dynamicScopesAuthenticationValidator());
                    p.setAuthorizationConsentRequired(dynamicScopesConsentValidator());
                  })));
          });

        return http.build();
    }

    private Consumer<OAuth2AuthorizationCodeRequestAuthenticationContext> dynamicScopesAuthenticationValidator() {

        return ctx -> {
            log.info("Dynamic scopes authentication validator invoked for client: {}, requested scopes: {}",
              ctx.getAuthorizationRequest().getClientId(),
              ctx.getAuthorizationRequest().getScopes());

            OAuth2AuthorizationCodeRequestAuthenticationToken auth = ctx.getAuthentication();
            var requestedScopes = new HashSet<>(auth.getScopes()); //
            var registeredClient = ctx.getRegisteredClient();
            var allowedScopes = registeredClient.getScopes();

            if ( requestedScopes.isEmpty() ) {
                // No scopes requested. This is fine.
                return;
            }

            // Filter out dynamic scopes from the requested scopes. We will handle them separately.
            requestedScopes.removeIf(allowedScopes::contains);

            if (requestedScopes.isEmpty() ) {
                // Request contains only static scopes. This is fine.
                return;
            }

            // Now, let's validate the remaining scopes using the provided validation service
            try {
                if (!dynamicScopeService.validate(registeredClient.getId(), requestedScopes)) {
                    throw new OAuth2AuthorizationCodeRequestAuthenticationException(new OAuth2Error(OAuth2ErrorCodes.INVALID_SCOPE), auth);
                }
            } catch (Exception ex) {
                // Spring Security requires that any error should be reported wrapped in an OAuth2AuthorizationCodeRequestAuthenticationException,
                // so we do that here.
                throw new OAuth2AuthorizationCodeRequestAuthenticationException(new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR), ex, auth);
            }
        };


    }

    private Predicate<OAuth2AuthorizationCodeRequestAuthenticationContext> dynamicScopesConsentValidator() {
        return ctx -> {

            var lastConsent = ctx.getAuthorizationConsent();

            if ( lastConsent == null ) {
                // First consent, so consent is required
                return true;
            }

            var alreadyConsented = new HashSet<>(lastConsent.getScopes());


            OAuth2AuthorizationCodeRequestAuthenticationToken auth = ctx.getAuthentication();
            var requestedScopes = new HashSet<>(auth.getScopes()); //

            if ( requestedScopes.isEmpty() ) {
                // No scopes requested, so no consent required
                return false;
            }

            // Remove already consented scopes from the requested scopes.
            requestedScopes.removeIf(alreadyConsented::contains);


            if (requestedScopes.isEmpty() ) {
                // Request contains only previously consented scopes. No consent required.
                return false;
            }

            // Any remaining scopes are dynamic scopes or static ones with no previous consent, so consent is required.
            return true;

        };
    }

}
