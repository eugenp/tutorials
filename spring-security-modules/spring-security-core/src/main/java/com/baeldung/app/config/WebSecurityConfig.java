package com.baeldung.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/css/**", "/js/**", "/loggedout")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .httpBasic()
            .and()
            .logout()
            .disable()
            .csrf()
            .disable();
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails jim = User.withUsername("jim")
            .password(passwordEncoder.encode("jim"))
            .roles("USER", "ACTUATOR")
            .build();

        UserDetails pam = User.withUsername("pam")
            .password(passwordEncoder.encode("pam"))
            .roles("USER")
            .build();

        UserDetails michael = User.withUsername("michael")
            .password(passwordEncoder.encode("michael"))
            .roles("MANAGER")
            .build();

        return new InMemoryUserDetailsManager(jim, pam, michael);
    }
}
