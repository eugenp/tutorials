package com.baeldung.propertiesvsyaml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(UserConfigurationProperties.class)
@SpringBootApplication
public class PropertiesApplication {

    public static void main(String[] args) {
        SpringApplication.run(PropertiesApplication.class, args);
    }

}
