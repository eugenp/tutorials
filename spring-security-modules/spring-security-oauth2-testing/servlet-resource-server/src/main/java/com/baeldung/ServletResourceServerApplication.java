package com.baeldung;

import static org.springframework.security.config.Customizer.withDefaults;

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
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
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
        SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.oauth2ResourceServer(resourceServer -> resourceServer.jwt(withDefaults()));
            http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            http.csrf(csrf -> csrf.disable());
            http.exceptionHandling(eh -> eh.authenticationEntryPoint((request, response, authException) -> {
                response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer realm=\"Restricted Content\"");
                response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
            }));

            // @formatter:off
            http.authorizeHttpRequests(req -> req
                    .requestMatchers(new AntPathRequestMatcher("/secured-route")).hasRole("AUTHORIZED_PERSONNEL")
                    .anyRequest().authenticated());
            // @formatter:on

            return http.build();
        }

        static interface JwtAuthoritiesConverter extends Converter<Jwt, Collection<GrantedAuthority>> {
        }

        @Bean
        JwtAuthoritiesConverter realmRoles2AuthoritiesConverter() {
            return (Jwt jwt) -> {
                final var realmRoles = Optional.of(jwt.getClaimAsMap("realm_access")).orElse(Map.of());
                @SuppressWarnings("unchecked")
                final var roles = (List<String>) realmRoles.getOrDefault("roles", List.of());
                return roles.stream().map(SimpleGrantedAuthority::new).map(GrantedAuthority.class::cast).toList();
            };
        }

        @Bean
        JwtAuthenticationConverter authenticationConverter(Converter<Jwt, Collection<GrantedAuthority>> authoritiesConverter) {
            final var authenticationConverter = new JwtAuthenticationConverter();
            authenticationConverter.setPrincipalClaimName(StandardClaimNames.PREFERRED_USERNAME);
            authenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
            return authenticationConverter;
        }
    }

    @Service
    public static class MessageService {

        public String greet() {
            final var who = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            return "Hello %s! You are granted with %s.".formatted(who.getName(), who.getAuthorities());
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
