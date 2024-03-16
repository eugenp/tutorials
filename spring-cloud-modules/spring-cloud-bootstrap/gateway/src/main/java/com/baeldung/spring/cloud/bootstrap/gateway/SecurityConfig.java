package com.baeldung.spring.cloud.bootstrap.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .build();
        UserDetails adminUser = User.withUsername("admin")
            .password(passwordEncoder().encode("admin"))
            .roles("ADMIN")
            .build();
        return new MapReactiveUserDetailsService(user, adminUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    return http.formLogin(form -> form.authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler("/home/browser/index.html")))
        .authorizeExchange(exchange -> exchange.pathMatchers("/book-service/**", "/rating-service/**", "/login*", "/")
            .permitAll()
            .pathMatchers("/eureka/**")
            .hasRole("ADMIN")
            .anyExchange()
            .authenticated())
        .logout(Customizer.withDefaults())
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .httpBasic(Customizer.withDefaults())
        .build();
    }
}
