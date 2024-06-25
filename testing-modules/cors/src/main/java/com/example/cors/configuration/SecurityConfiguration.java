package com.example.cors.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final String[] WHITELISTED_API_ENDPOINTS = { "/api/v1/joke" };

    @Bean
    @SneakyThrows
    public SecurityFilterChain configure(final HttpSecurity http) {
        http
          .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
          .authorizeHttpRequests(authManager -> {
            authManager.requestMatchers(WHITELISTED_API_ENDPOINTS).permitAll().anyRequest().authenticated();
          });
        return http.build();
    }

    private CorsConfigurationSource corsConfigurationSource() {
        final var corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("https://baeldung.com"));
        corsConfiguration.setAllowedMethods(List.of("GET"));
        corsConfiguration.setAllowedHeaders(List.of("X-Baeldung-Key"));
        corsConfiguration.setExposedHeaders(List.of("X-Rate-Limit-Remaining"));

        final var corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return corsConfigurationSource;
    }

}