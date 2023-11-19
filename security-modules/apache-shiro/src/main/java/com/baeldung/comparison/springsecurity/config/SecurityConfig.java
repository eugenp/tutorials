package com.baeldung.comparison.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
            .disable()
            .authorizeRequests(authorize -> authorize.antMatchers("/index", "/login")
                .permitAll()
                .antMatchers("/home", "/logout")
                .authenticated()
                .antMatchers("/admin/**")
                .hasRole("ADMIN"))
            .formLogin(formLogin -> formLogin.loginPage("/login")
                .failureUrl("/login-error"));
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() throws Exception {
        UserDetails jerry = User.withUsername("Jerry")
            .password(passwordEncoder().encode("password"))
            .authorities("READ", "WRITE")
            .roles("ADMIN")
            .build();
        UserDetails tom = User.withUsername("Tom")
            .password(passwordEncoder().encode("password"))
            .authorities("READ")
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(jerry, tom);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
