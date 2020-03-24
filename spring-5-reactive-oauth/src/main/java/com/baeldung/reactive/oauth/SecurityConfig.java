package com.baeldung.reactive.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {

        @Bean
        public SecurityWebFilterChain configure(ServerHttpSecurity http) throws Exception {
            return http.authorizeExchange()
                .pathMatchers("/about").permitAll()
                .anyExchange().authenticated()
                .and().oauth2Login()
                .and().build();
        }
}
