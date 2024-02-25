package com.baeldung.permitallanonymous.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.baeldung.permitallanonymous.filter.AuditInterceptor;

@Configuration
@EnableWebSecurity
public class EcommerceWebSecurityConfig {
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("spring")
                .password(passwordEncoder.encode("secret"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.addFilterAfter(new AuditInterceptor(), AnonymousAuthenticationFilter.class)
            .authorizeHttpRequests(request -> request.requestMatchers(new AntPathRequestMatcher("/private/**"))
                .authenticated())
            .httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests(request -> request.requestMatchers(new AntPathRequestMatcher("/public/showProducts"))
                .permitAll())
            .authorizeHttpRequests(request -> request.requestMatchers(new AntPathRequestMatcher("/public/registerUser"))
                .anonymous())
            .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}