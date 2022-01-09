package com.baeldung.reactive.debugging.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.Collections;

@EnableWebFlux
@SpringBootApplication
public class ServerDebuggingApplication {
    
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ServerDebuggingApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        app.run(args);
    }
    
    @Bean
    public SecurityWebFilterChain debuggingServerSpringSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
            .anyExchange()
            .permitAll();
        return http.build();
    }
}
