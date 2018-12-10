package com.baeldung.debugging.consumer;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import reactor.core.publisher.Hooks;

@SpringBootApplication(exclude = MongoReactiveAutoConfiguration.class)
@EnableScheduling
public class ConsumerDebuggingApplication {

    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        SpringApplication app = new SpringApplication(ConsumerDebuggingApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8082"));
        app.run(args);
    }

    @Bean
    public SecurityWebFilterChain debuggingConsumerSpringSecurityFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange()
            .anyExchange()
            .permitAll();
        return http.build();
    }
}
