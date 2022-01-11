package com.baeldung.springcloudgateway.oauth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@SpringBootApplication
@PropertySource("classpath:resource-server-application.yml")
@EnableWebFluxSecurity
public class ResourceServerGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResourceServerGatewayApplication.class,args);
    }
}
