package com.baeldung.properties;

import com.baeldung.buildproperties.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import com.baeldung.configurationproperties.ConfigProperties;

@SpringBootApplication
@ComponentScan(basePackageClasses = {ConfigProperties.class, AdditionalProperties.class})
public class ConfigPropertiesDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigPropertiesDemoApplication.class, args);
    }

}
