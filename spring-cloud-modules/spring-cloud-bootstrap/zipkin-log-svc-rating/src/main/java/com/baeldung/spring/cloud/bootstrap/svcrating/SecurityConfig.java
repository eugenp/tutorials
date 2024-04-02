package com.baeldung.spring.cloud.bootstrap.svcrating;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService users() {
        return new InMemoryUserDetailsManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests((auth) -> auth.requestMatchers("^/ratings\\?bookId.*$")
                .authenticated()
                .requestMatchers(HttpMethod.POST, "/ratings")
                .authenticated()
                .requestMatchers(HttpMethod.PATCH, "/ratings/*")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/ratings/*")
                .hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/ratings")
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated())
            .httpBasic(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .build();
    }
}
