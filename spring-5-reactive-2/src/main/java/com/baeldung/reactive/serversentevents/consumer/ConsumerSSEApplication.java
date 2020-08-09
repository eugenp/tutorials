package com.baeldung.reactive.serversentevents.consumer;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication(exclude = { RedisReactiveAutoConfiguration.class })
@EnableAsync
public class ConsumerSSEApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ConsumerSSEApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8082"));
        app.run(args);
    }
    
    @Bean
    public SecurityWebFilterChain sseConsumerSpringSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
            .anyExchange()
            .permitAll();
        return http.build();
    }

}
