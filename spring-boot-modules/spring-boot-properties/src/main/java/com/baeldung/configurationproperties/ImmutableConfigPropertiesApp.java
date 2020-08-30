package com.baeldung.configurationproperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ImmutableConfigPropertiesApp {

    public static void main(String[] args) {
        SpringApplication.run(ImmutableConfigPropertiesApp.class, args);
    }
}
