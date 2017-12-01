package com.baeldung.spring.cloud.consul;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
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
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/discoveryClient")
    public String home() {
        return this.restTemplate.getForEntity(serviceUrl().resolve("/ping"), String.class)
            .getBody();
    }

    public URI serviceUrl() {
        List<ServiceInstance> list = discoveryClient.getInstances("myApp");
        if (list != null && list.size() > 0) {
            return list.get(0)
                .getUri();
        }

        return null;
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryClientApplication.class).web(true)
            .run(args);
    }
}
