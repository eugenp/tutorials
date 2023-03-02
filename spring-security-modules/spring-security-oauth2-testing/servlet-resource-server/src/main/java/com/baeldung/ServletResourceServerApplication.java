package com.baeldung;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
public class ServletResourceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServletResourceServerApplication.class, args);
    }

    @Configuration
    @EnableMethodSecurity
    @EnableWebSecurity
    static class SecurityConf {
        @Bean
        SecurityFilterChain filterChain(HttpSecurity http, Converter<Jwt, Collection<GrantedAuthority>> authoritiesConverter) throws Exception {
            http.oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwt -> new JwtAuthenticationToken(jwt, authoritiesConverter.convert(jwt)));
            http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable();
            http.exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Restricted Content\"");
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
                });

            http.authorizeHttpRequests()
                .requestMatchers("/secured-route")
                .hasRole("AUTHORIZED_PERSONNEL")
                .anyRequest()
                .authenticated();

            return http.build();
        }

        static interface AuthoritiesConverter extends Converter<Jwt, Collection<GrantedAuthority>> {
        }

        @Bean
        AuthoritiesConverter realmRoles2AuthoritiesConverter() {
            return (Jwt jwt) -> {
                final var realmRoles = Optional.of(jwt.getClaimAsMap("realm_access"))
                    .orElse(Map.of());
                @SuppressWarnings("unchecked")
                final var roles = (List<String>) realmRoles.getOrDefault("roles", List.of());
                return roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .map(GrantedAuthority.class::cast)
                    .toList();
            };
        }
    }

    @Service
    public static class MessageService {

        public String greet() {
            final var who = (JwtAuthenticationToken) SecurityContextHolder.getContext()
                .getAuthentication();
            final var claims = who.getTokenAttributes();
            return "Hello %s! You are granted with %s.".formatted(claims.getOrDefault(StandardClaimNames.PREFERRED_USERNAME, claims.get(StandardClaimNames.SUB)), who.getAuthorities());
        }

        @PreAuthorize("hasRole('AUTHORIZED_PERSONNEL')")
        public String getSecret() {
            return "Only authorized personnel can read that";
        }
    }

    @RestController
    @RequiredArgsConstructor
    public static class GreetingController {
        private final MessageService messageService;

        @GetMapping("/greet")
        public ResponseEntity<String> greet() {
            return ResponseEntity.ok(messageService.greet());
        }

        @GetMapping("/secured-route")
        public ResponseEntity<String> securedRoute() {
            return ResponseEntity.ok(messageService.getSecret());
        }

        @GetMapping("/secured-method")
        @PreAuthorize("hasRole('AUTHORIZED_PERSONNEL')")
        public ResponseEntity<String> securedMethod() {
            return ResponseEntity.ok(messageService.getSecret());
        }
    }

}
