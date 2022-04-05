package com.baeldung.spring.cloud.consul.health;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import static org.springframework.boot.WebApplicationType.NONE;

@SpringBootApplication
public class ServiceDiscoveryApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ServiceDiscoveryApplication.class).web(NONE)
            .run(args);
    }

}