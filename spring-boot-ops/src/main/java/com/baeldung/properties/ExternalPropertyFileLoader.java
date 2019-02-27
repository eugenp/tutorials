package com.baeldung.properties;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
public class ExternalPropertyFileLoader {
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(ExternalPropertyFileLoader.class).properties("spring.config.name:conf", "spring.config.location:file:src/main/resources/external/")
            .build()
            .run(args);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        environment.getProperty("username");
    }
}
