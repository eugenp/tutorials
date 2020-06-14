package com.baeldung.hexagonal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@PropertySource(value = { "classpath:ddd-layers.properties" })
@ComponentScan({"com.baeldung.hexagonal.infrastructure", "com.baeldung.hexagonal.application"})
public class DomainLayerApplication  {

    public static void main(final String[] args) {
        SpringApplication application = new SpringApplication(DomainLayerApplication.class);
        // uncomment to run just the console application
        // application.setWebApplicationType(WebApplicationType.NONE);
        application.run(args);
    }

}
