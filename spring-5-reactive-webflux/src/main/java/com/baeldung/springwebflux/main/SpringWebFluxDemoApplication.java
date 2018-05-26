package com.baeldung.springwebflux.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.baeldung" })
@EnableAutoConfiguration
public class SpringWebFluxDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebFluxDemoApplication.class, args);
    }
}
