package com.baeldung.spring.cloud.consul.discovery;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class DiscoveryClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryClientApplication.class).web(true)
            .run(args);
    }

}
