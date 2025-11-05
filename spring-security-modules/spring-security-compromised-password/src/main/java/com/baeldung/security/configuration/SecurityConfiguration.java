package com.baeldung.security.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final String[] WHITELISTED_API_ENDPOINTS = { "/users" };
    private static final String CUSTOM_COMPROMISED_PASSWORD_CHECK_URL = "https://api.example.com/password-check";

    @Bean
    @SneakyThrows
    public SecurityFilterChain configure(final HttpSecurity http) {
        http.csrf(csrfConfigurer -> csrfConfigurer.disable())
                .sessionManagement(
                        sessionConfigurer -> sessionConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authManager -> {
                    authManager.requestMatchers(WHITELISTED_API_ENDPOINTS).permitAll().anyRequest().authenticated();
                });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

    @Bean
    @ConditionalOnMissingBean
    public CompromisedPasswordChecker customCompromisedPasswordChecker() {
        RestClient customRestClient = RestClient.builder().baseUrl(CUSTOM_COMPROMISED_PASSWORD_CHECK_URL)
                .defaultHeader("X-API-KEY", "api-key").build();

        HaveIBeenPwnedRestApiPasswordChecker compromisedPasswordChecker = new HaveIBeenPwnedRestApiPasswordChecker();
        compromisedPasswordChecker.setRestClient(customRestClient);
        return compromisedPasswordChecker;
    }

}