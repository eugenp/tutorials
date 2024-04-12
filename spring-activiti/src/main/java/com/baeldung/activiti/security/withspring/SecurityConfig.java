package com.baeldung.activiti.security.withspring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/protected-process*")
                .authenticated()
                .anyRequest()
                .permitAll())
            .formLogin(login -> login
                .loginPage("/login")
                .defaultSuccessUrl("/homepage")
                .failureUrl("/login?error=true")
                .permitAll())
            .csrf(AbstractHttpConfigurer::disable)
            .logout(logout -> logout.logoutSuccessUrl("/login"));
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        User.UserBuilder users = User.withDefaultPasswordEncoder();
        UserDetails user = users.username("user")
            .password("{noop}pass")
            .authorities("ROLE_ACTIVITI_USER")
            .build();

        return new InMemoryUserDetailsManager(user);
    }
}
