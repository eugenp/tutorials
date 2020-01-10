package com.baeldung.springcloudgateway.custompredicates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:customrouters-global-application.yml")
public class CustomRoutersGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomRoutersGatewayApplication.class, args);
    }

}