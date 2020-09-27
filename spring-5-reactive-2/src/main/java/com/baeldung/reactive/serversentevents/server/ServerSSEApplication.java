package com.baeldung.reactive.serversentevents.server;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication(exclude = { RedisReactiveAutoConfiguration.class })
public class ServerSSEApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ServerSSEApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        app.run(args);
    }

    @Bean
    public SecurityWebFilterChain sseServerSpringSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
            .anyExchange()
            .permitAll();
        return http.build();
    }

}
