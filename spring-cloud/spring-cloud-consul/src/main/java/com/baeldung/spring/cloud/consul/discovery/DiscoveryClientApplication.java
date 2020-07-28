package com.baeldung.spring.cloud.consul.discovery;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import static org.springframework.boot.WebApplicationType.NONE;

@SpringBootApplication
@EnableDiscoveryClient
public class DiscoveryClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DiscoveryClientApplication.class).web(NONE)
            .run(args);
    }

}
