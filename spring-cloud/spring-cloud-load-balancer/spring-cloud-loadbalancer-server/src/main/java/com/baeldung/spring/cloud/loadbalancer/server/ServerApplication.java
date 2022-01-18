package com.baeldung.spring.cloud.loadbalancer.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Value("${server.instance.id}")
    String instanceId;

    @GetMapping("/hello")
    public String hello()
    {
        return String.format("Hello from instance %s", instanceId);
    }
}
