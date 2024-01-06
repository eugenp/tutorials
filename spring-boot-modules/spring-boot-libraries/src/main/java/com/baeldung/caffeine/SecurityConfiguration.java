package com.baeldung.caffeine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Because the POM imports Spring Security, we need a simple security
 * configuration for this example application to allow all HTTP requests.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
        http.csrf().disable();

        return http.authorizeRequests()
                .antMatchers("/**")
                .permitAll().and().build();
    }
}
