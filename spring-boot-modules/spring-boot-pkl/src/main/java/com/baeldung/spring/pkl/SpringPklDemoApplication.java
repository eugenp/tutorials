package com.baeldung.spring.pkl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringPklDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringPklDemoApplication.class, args);
    }

}
