package com.baeldung.resttemplate.custom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan(basePackages = "com.baeldung.resttemplate.custom")
@PropertySource("classpath:application.properties")
@SpringBootApplication
public class RestTemplateClientApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(RestTemplateClientApplication.class);
        application.setAdditionalProfiles("ssl-client");
        application.run(args);
    }
}
