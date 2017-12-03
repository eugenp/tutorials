package com.baeldung.spring.cloud.consul.discovery;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class DiscoveryClientApplication {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/discoveryClient")
    public String home() {
        return restTemplate().getForEntity(serviceUrl().resolve("/ping"), String.class)
            .getBody();
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @RequestMapping("/my-health-check")
    public ResponseEntity<String> myCustomCheck() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public URI serviceUrl() {
        return discoveryClient.getInstances("myApp")
            .stream()
            .findFirst()
            .map(si -> si.getUri())
            .orElse(null);
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryClientApplication.class).web(true)
            .run(args);
    }
}
