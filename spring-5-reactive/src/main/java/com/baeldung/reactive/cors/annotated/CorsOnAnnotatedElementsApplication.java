package com.baeldung.reactive.cors.annotated;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication
public class CorsOnAnnotatedElementsApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CorsOnAnnotatedElementsApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        app.run(args);
    }

    @Bean
    public SecurityWebFilterChain corsAnnotatedSpringSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf().disable();
        return http.build();
    }
}
