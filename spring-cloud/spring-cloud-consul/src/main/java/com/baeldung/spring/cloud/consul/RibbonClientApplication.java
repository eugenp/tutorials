package com.baeldung.spring.cloud.consul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class RibbonClientApplication {

    @LoadBalanced
    @Bean
    public RestTemplate loadbalancedRestTemplate() {
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/ribbonClient")
    public String home() {
        return this.restTemplate.getForObject("http://myApp/ping", String.class);
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(RibbonClientApplication.class).web(true)
            .run(args);
    }
}
