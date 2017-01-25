package com.baeldung.spring.cloud.bootstrap.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClientSpecification;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class GatewayApplication {
        public static void main(String[] args) {
                SpringApplication.run(GatewayApplication.class, args);
        }

        @Autowired(required = false)
        private List<RibbonClientSpecification> configurations = new ArrayList<>();

        @Bean
        @LoadBalanced
        RestTemplate restTemplate() {
                return new RestTemplate();
        }

        @Bean
        public SpringClientFactory springClientFactory() {
                SpringClientFactory factory = new SpringClientFactory();
                factory.setConfigurations(this.configurations);
                return factory;
        }
}
