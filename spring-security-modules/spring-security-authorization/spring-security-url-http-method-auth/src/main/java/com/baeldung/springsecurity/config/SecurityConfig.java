package com.baeldung.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          .csrf(csrf -> csrf.disable()) // Disable CSRF protection completely
          .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
          .authorizeHttpRequests(auth -> auth
            .requestMatchers(new AntPathRequestMatcher("/users/register")).permitAll()
            .requestMatchers(new AntPathRequestMatcher("/h2-console/**")).permitAll()
            .requestMatchers(HttpMethod.GET, "/users/profile").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.GET, "/posts/mine").hasRole("USER")
            .requestMatchers(HttpMethod.POST, "/posts/create").hasRole("USER")
            .requestMatchers(HttpMethod.PUT, "/posts/**").hasRole("USER")
            .requestMatchers(HttpMethod.DELETE, "/posts/**").hasAnyRole("USER", "ADMIN")
            .anyRequest().authenticated()
          )
        .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
