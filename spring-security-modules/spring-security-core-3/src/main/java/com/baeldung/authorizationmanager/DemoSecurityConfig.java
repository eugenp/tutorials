package com.baeldung.authorizationmanager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.Random;
import java.util.function.Supplier;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
public class DemoSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/custom/**").access(customAuthManager())
            .requestMatchers("/adminonly/**").hasRole("ADMIN")
            .requestMatchers("/editororauthor/**").hasAnyRole("EDITOR","AUTHOR")
            .anyRequest().permitAll())
            .formLogin(withDefaults());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.withUsername("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("ADMIN")
            .build();
        UserDetails author = User.withUsername("author")
            .password(passwordEncoder().encode("author"))
            .roles("AUTHOR")
            .build();
        UserDetails editor = User.withUsername("editor")
            .password(passwordEncoder().encode("editor"))
            .roles("EDITOR")
            .build();
        return new InMemoryUserDetailsManager(admin, author, editor);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthorizationManager<RequestAuthorizationContext> customAuthManager() {
        return (authentication, object) -> new AuthorizationDecision(new Random().nextBoolean());
    }
}

