package com.baeldung.security.opa.config;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ImmutableMap;

import reactor.core.publisher.Mono;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain accountAuthorization(ServerHttpSecurity http, @Qualifier("opaWebClient") WebClient opaWebClient) {
        return http.httpBasic(Customizer.withDefaults())
            .authorizeExchange(exchanges -> exchanges.pathMatchers("/account/*")
                .access(opaAuthManager(opaWebClient)))
            .build();
    }

    @Bean
    public ReactiveAuthorizationManager<AuthorizationContext> opaAuthManager(WebClient opaWebClient) {
        return (auth, context) -> opaWebClient.post()
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(toAuthorizationPayload(auth, context), Map.class)
            .exchangeToMono(this::toDecision);
    }

    private Mono<AuthorizationDecision> toDecision(ClientResponse response) {
        if (!response.statusCode()
            .is2xxSuccessful()) {
            return Mono.just(new AuthorizationDecision(false));
        }

        return response.bodyToMono(ObjectNode.class)
            .map(node -> {
                boolean authorized = node.path("result")
                    .path("authorized")
                    .asBoolean(false);
                return new AuthorizationDecision(authorized);
            });

    }

    private Publisher<Map<String, Object>> toAuthorizationPayload(Mono<Authentication> auth, AuthorizationContext context) {
        return auth.defaultIfEmpty(
                new AnonymousAuthenticationToken("**ANONYMOUS**", new Object(), Collections.singletonList(new SimpleGrantedAuthority("ANONYMOUS"))))
            .map(a -> {
                Map<String, String> headers = context.getExchange()
                    .getRequest()
                    .getHeaders()
                    .toSingleValueMap();

                Map<String, Object> attributes = ImmutableMap.<String, Object> builder()
                    .put("principal", a.getName())
                    .put("authorities", a.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                    .put("uri", context.getExchange()
                        .getRequest()
                        .getURI()
                        .getPath())
                    .put("headers", headers)
                    .build();

                return ImmutableMap.<String, Object> builder()
                    .put("input", attributes)
                    .build();
            });
    }
}
