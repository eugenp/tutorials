package com.baeldung.auth.server.dynamicscopes.config;

import com.baeldung.auth.server.dynamicscopes.components.DynamicScopeService;
import org.slf4j.Logger;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.security.autoconfigure.web.servlet.SecurityFilterProperties;
import org.springframework.boot.security.oauth2.server.authorization.autoconfigure.servlet.OAuth2AuthorizationServerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationContext;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationException;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationProvider;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class AuthServerConfiguration {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(AuthServerConfiguration.class);

    private final DynamicScopeService dynamicScopeService;

    public AuthServerConfiguration(DynamicScopeService dynamicScopeService) {
        this.dynamicScopeService = dynamicScopeService;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) {

        log.info("Creating custom authorizationServer configuration");

        http.oauth2AuthorizationServer(authorizationServer -> {
            http.securityMatcher(authorizationServer.getEndpointsMatcher());

            authorizationServer
              .oidc(withDefaults())
              .authorizationEndpoint(ap -> {
                  ap.consentPage("/consent");
                  ap.authenticationProviders(providers -> {
                      providers.stream()
                        .filter(OAuth2AuthorizationCodeRequestAuthenticationProvider.class::isInstance)
                        .map(p -> (OAuth2AuthorizationCodeRequestAuthenticationProvider)p)
                        .findFirst()
                        .ifPresent(p -> {
                            p.setAuthenticationValidator(dynamicScopesAuthenticationValidator());
                            p.setAuthorizationConsentRequired(dynamicScopesConsentValidator());
                        });
                  });
              });

        });
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        http.oauth2ResourceServer(resourceServer -> resourceServer.jwt(withDefaults()));
        http.exceptionHandling(exceptions -> exceptions.defaultAuthenticationEntryPointFor(
          new LoginUrlAuthenticationEntryPoint("/login"), createRequestMatcher()));
        return http.build();
    }

    @Bean
    @Order(SecurityFilterProperties.BASIC_AUTH_ORDER)
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) {
        http.authorizeHttpRequests(authorize -> {
            authorize.anyRequest().authenticated();
          })
          .formLogin(withDefaults());
        return http.build();
    }

    private static RequestMatcher createRequestMatcher() {
        MediaTypeRequestMatcher requestMatcher = new MediaTypeRequestMatcher(MediaType.TEXT_HTML);
        requestMatcher.setIgnoredMediaTypes(Set.of(MediaType.ALL));
        return requestMatcher;
    }


    private Consumer<OAuth2AuthorizationCodeRequestAuthenticationContext> dynamicScopesAuthenticationValidator() {

        return ctx -> {

            OAuth2AuthorizationCodeRequestAuthenticationToken auth = ctx.getAuthentication();
            var registeredClient = ctx.getRegisteredClient();

            var requestedScopes = new HashSet<>(auth.getScopes());
            if ( requestedScopes.isEmpty() ) {
                // No scopes requested. This is fine.
                return;
            }

            // Filter out dynamic scopes from the requested scopes. We will handle them separately.
            var allowedScopes = registeredClient.getScopes();
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

            OAuth2AuthorizationCodeRequestAuthenticationToken auth = ctx.getAuthentication();
            var requestedScopes = new HashSet<>(auth.getScopes()); //
            if ( requestedScopes.isEmpty() ) {
                // No scopes requested, so no consent required
                return false;
            }

            // Remove already consented scopes
            var alreadyConsented = new HashSet<>(lastConsent.getScopes());
            requestedScopes.removeIf(alreadyConsented::contains);
            if (requestedScopes.isEmpty() ) {
                // Request contains only previously consented scopes. No consent required.
                return false;
            }

            // Any remaining scopes are dynamic scopes or static ones with no previous consent. Ask the service
            return dynamicScopeService.isConsentNeeded(ctx.getRegisteredClient().getId(), requestedScopes);
        };
    }

    @Bean
    OAuth2AuthorizationConsentService dynamicScopesConsentService() {
        return new InMemoryOAuth2AuthorizationConsentService();
    }



}
