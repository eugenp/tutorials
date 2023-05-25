package com.baeldung.spring.cloud.config.docker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DockerServer {
    public static void main(String[] args) {
        SpringApplication.run(DockerServer.class, args);
    }
}
