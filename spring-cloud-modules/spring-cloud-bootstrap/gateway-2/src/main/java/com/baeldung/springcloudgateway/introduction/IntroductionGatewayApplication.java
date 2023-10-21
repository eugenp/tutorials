package com.baeldung.springcloudgateway.introduction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:introduction-application.properties")
public class IntroductionGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntroductionGatewayApplication.class, args);
    }

}