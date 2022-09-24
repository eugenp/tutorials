package com.baeldung.roles.ip.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomIpAuthenticationProvider authenticationProvider;

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("john")
            .password("{noop}123")
            .authorities("ROLE_USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/login")
            .permitAll()
            // .antMatchers("/foos/**").hasIpAddress("11.11.11.11")
            .antMatchers("/foos/**")
            .access("isAuthenticated() and hasIpAddress('11.11.11.11')")
            .anyRequest()
            .authenticated()
            .and()
            .formLogin()
            .permitAll()
            .and()
            .csrf()
            .disable();
        return http.build();
    }
}