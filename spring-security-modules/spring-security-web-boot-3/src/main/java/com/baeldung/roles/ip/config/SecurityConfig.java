package com.baeldung.roles.ip.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomIpAuthenticationProvider authenticationProvider;

    @Bean
    public InMemoryUserDetailsManager userDetailsService(HttpSecurity http) throws Exception {
        UserDetails user = User.withUsername("john")
            .password("{noop}123")
            .authorities("ROLE_USER")
            .build();
        http.getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(authenticationProvider)
            .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry.requestMatchers("/login").permitAll()
                        .requestMatchers("/foos/**")
                        .access(new WebExpressionAuthorizationManager("isAuthenticated() and hasIpAddress('11.11.11.11')")).anyRequest().authenticated())
            .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
            .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}