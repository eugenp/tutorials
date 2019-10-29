package com.baeldung.validations.functional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication
public class FunctionalValidationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FunctionalValidationsApplication.class, args);
    }
    
    @Bean
    public SecurityWebFilterChain functionalValidationsSpringSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
            .anyExchange()
            .permitAll();
        http.csrf().disable();
        return http.build();
    }
}
