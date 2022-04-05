package com.baeldung.spring.cloud.consul.properties;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.boot.WebApplicationType.NONE;

@SpringBootApplication
@RestController
public class DistributedPropertiesApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DistributedPropertiesApplication.class).web(NONE)
            .run(args);
    }

}
