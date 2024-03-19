package com.baeldung.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(1)
public class SecurityConfig {
    
    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception { // @formatter:off
        http.securityMatcher("/login", "/oauth/authorize")
            .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                    authorizationManagerRequestMatcherRegistry.anyRequest().authenticated())
            .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
            .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    } // @formatter:on

    @Bean
    protected UserDetailsService configure(AuthenticationManagerBuilder auth) { // @formatter:off
        User.UserBuilder users = User.builder();
        UserDetails user = users
                .username("john")
                .password(passwordEncoder().encode("123"))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    } // @formatter:on

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
