package com.baeldung.tokenexchange.userservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestClient;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          .authorizeHttpRequests(authorize -> authorize
            .anyRequest().authenticated())
          .csrf(AbstractHttpConfigurer::disable)
          .sessionManagement(session ->
              session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .oauth2ResourceServer(resource -> resource.jwt(Customizer.withDefaults()))
          .oauth2Client(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
          .build();
    }

}
