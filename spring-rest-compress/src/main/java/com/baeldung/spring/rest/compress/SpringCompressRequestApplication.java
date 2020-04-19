package com.baeldung.spring.rest.compress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class SpringCompressRequestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCompressRequestApplication.class, args);
    }

}
