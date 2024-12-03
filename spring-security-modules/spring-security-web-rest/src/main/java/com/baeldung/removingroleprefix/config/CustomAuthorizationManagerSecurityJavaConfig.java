package com.baeldung.removingroleprefix.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class CustomAuthorizationManagerSecurityJavaConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests (authorizeRequests ->
              hasRole(authorizeRequests.requestMatchers("/test-resource"), "ADMIN"))
            .httpBasic(withDefaults());


        return http.build();
    }

    private void hasRole(AuthorizeHttpRequestsConfigurer.AuthorizedUrl authorizedUrl, String role) {
        authorizedUrl.access(new CustomAuthorizationManager().withRole(role));
    }
}
