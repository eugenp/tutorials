package com.baeldung.projection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("persistence-student")
public class ProjectionApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProjectionApplication.class);
    }
}
