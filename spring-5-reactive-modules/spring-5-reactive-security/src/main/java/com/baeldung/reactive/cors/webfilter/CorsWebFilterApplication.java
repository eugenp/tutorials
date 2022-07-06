package com.baeldung.reactive.cors.webfilter;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication(exclude = { MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class,
        MongoReactiveDataAutoConfiguration.class, 
        MongoReactiveAutoConfiguration.class }
)
public class CorsWebFilterApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(CorsWebFilterApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));
        app.run(args);
    }
    
    @Bean
    public SecurityWebFilterChain corsWebfilterSpringSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf().disable();
        return http.build();
    }

}
