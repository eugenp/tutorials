package com.baeldung.springcloudgateway.custompredicates;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CustomRoutersGatewayApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CustomRoutersGatewayApplication.class)
        .profiles("customroutes")
        .run(args);
    }

}