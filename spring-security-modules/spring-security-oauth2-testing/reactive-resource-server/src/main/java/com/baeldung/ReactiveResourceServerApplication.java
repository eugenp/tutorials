package com.baeldung;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ReactiveResourceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveResourceServerApplication.class, args);
    }

    @Configuration
    @EnableWebFluxSecurity
    @EnableReactiveMethodSecurity
    public class SecurityConfig {
        @Bean
        SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http, Converter<Jwt, Mono<Collection<GrantedAuthority>>> authoritiesConverter) {
            http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwt -> authoritiesConverter.convert(jwt)
                    .map(authorities -> new JwtAuthenticationToken(jwt, authorities)));
            http.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .csrf()
                .disable();
            http.exceptionHandling()
                .accessDeniedHandler((var exchange, var ex) -> exchange.getPrincipal()
                    .flatMap(principal -> {
                        final var response = exchange.getResponse();
                        response.setStatusCode(principal instanceof AnonymousAuthenticationToken ? HttpStatus.UNAUTHORIZED : HttpStatus.FORBIDDEN);
                        response.getHeaders()
                            .setContentType(MediaType.TEXT_PLAIN);
                        final var dataBufferFactory = response.bufferFactory();
                        final var buffer = dataBufferFactory.wrap(ex.getMessage()
                            .getBytes(Charset.defaultCharset()));
                        return response.writeWith(Mono.just(buffer))
                            .doOnError(error -> DataBufferUtils.release(buffer));
                    }));

            http.authorizeExchange()
                .pathMatchers("/secured-route")
                .hasRole("AUTHORIZED_PERSONNEL")
                .anyExchange()
                .authenticated();

            return http.build();
        }

        static interface AuthoritiesConverter extends Converter<Jwt, Mono<Collection<GrantedAuthority>>> {
        }

        @Bean
        AuthoritiesConverter realmRoles2AuthoritiesConverter() {
            return (Jwt jwt) -> {
                final var realmRoles = Optional.of(jwt.getClaimAsMap("realm_access"))
                    .orElse(Map.of());
                @SuppressWarnings("unchecked")
                final var roles = (List<String>) realmRoles.getOrDefault("roles", List.of());
                return Mono.just(roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .map(GrantedAuthority.class::cast)
                    .toList());
            };
        }
    }

    @Service
    public static class MessageService {

        public Mono<String> greet() {
            return ReactiveSecurityContextHolder.getContext()
                .map(ctx -> {
                    final var who = (JwtAuthenticationToken) ctx.getAuthentication();
                    final var claims = who.getTokenAttributes();
                    return "Hello %s! You are granted with %s.".formatted(claims.getOrDefault(StandardClaimNames.PREFERRED_USERNAME, claims.get(StandardClaimNames.SUB)), who.getAuthorities());
                })
                .switchIfEmpty(Mono.error(new AuthenticationCredentialsNotFoundException("Security context is empty")));
        }

        @PreAuthorize("hasRole('AUTHORIZED_PERSONNEL')")
        public Mono<String> getSecret() {
            return Mono.just("Only authorized personnel can read that");
        }
    }

    @RestController
    @RequiredArgsConstructor
    public class GreetingController {
        private final MessageService messageService;

        @GetMapping("/greet")
        public Mono<ResponseEntity<String>> greet() {
            return messageService.greet()
                .map(ResponseEntity::ok);
        }

        @GetMapping("/secured-route")
        public Mono<ResponseEntity<String>> securedRoute() {
            return messageService.getSecret()
                .map(ResponseEntity::ok);
        }

        @GetMapping("/secured-method")
        @PreAuthorize("hasRole('AUTHORIZED_PERSONNEL')")
        public Mono<ResponseEntity<String>> securedMethod() {
            return messageService.getSecret()
                .map(ResponseEntity::ok);
        }
    }

}
