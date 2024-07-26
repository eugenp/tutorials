package com.baeldung.springcloudgateway.introduction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication
@PropertySource("classpath:introduction-application.properties")
@EnableWebFluxSecurity
public class IntroductionGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntroductionGatewayApplication.class, args);
    }

}

@Configuration
 class SecurityConfig {

    @Bean
    SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange(c -> {
            c.anyExchange().permitAll();
        });
        return http.build();
    }
}
