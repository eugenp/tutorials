package com.baeldung.cloud.echo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@RestController
public class EchoApplication {
    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @GetMapping("/hello/{me}")
    public ResponseEntity<String> echo(@PathVariable("me") String me) {
        List<ServiceInstance> instances = discoveryClient.getInstances("sidecar");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("hello service is down");
        }
        String url = instances.get(0).getUri().toString();
        return ResponseEntity.ok(restTemplate.getForObject(url + "/hello/" + me, String.class));
    }

    public static void main(String[] args) {
        SpringApplication.run(EchoApplication.class, args);
    }
}
