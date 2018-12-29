package com.baeldung.webclient.authcode.login.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;

@Configuration
public class WebSecurityConfig {
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, ServerOAuth2AuthorizedClientRepository authorizedClients,
        ReactiveClientRegistrationRepository clientRegistrations) {
        http.authorizeExchange()
            .anyExchange()
            .authenticated()
            .and()
            .oauth2Login();
//            .authorizedClientRepository(authorizedClients)
//            .clientRegistrationRepository(clientRegistrations);
        return http.build();
    }
}
