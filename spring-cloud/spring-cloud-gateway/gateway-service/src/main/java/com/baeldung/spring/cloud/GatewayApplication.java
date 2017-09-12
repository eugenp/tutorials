package com.baeldung.spring.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class GatewayApplication {

    @Configuration
    @EnableDiscoveryClient
    protected static class GatewayDiscoveryConfiguration {
        @Bean
        public DiscoveryClientRouteDefinitionLocator discoveryClientRouteLocator(DiscoveryClient discoveryClient) {
            return new DiscoveryClientRouteDefinitionLocator(discoveryClient);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}