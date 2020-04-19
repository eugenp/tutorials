package com.baeldung.staticcontent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.Collections;

@SpringBootApplication
public class StaticContentApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(StaticContentApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8084"));
        app.run(args);
    }

    @Bean
    public SecurityWebFilterChain staticContentSpringSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
                .anyExchange()
                .permitAll();
        return http.build();
    }

}
