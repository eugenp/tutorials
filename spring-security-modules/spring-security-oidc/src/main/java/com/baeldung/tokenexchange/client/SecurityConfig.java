package com.baeldung.tokenexchange.client;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.http.SessionCreationPolicy.NEVER;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          .authorizeHttpRequests(authorize -> {authorize
            .requestMatchers("/login/**","/error/**").permitAll()
            .anyRequest().authenticated();
            })
          .csrf(AbstractHttpConfigurer::disable)
          .sessionManagement(session ->
            session.sessionCreationPolicy(NEVER))
          .oauth2Login(withDefaults())
          .oauth2Client(withDefaults());
        return http.build();
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
          .build();
    }
}
