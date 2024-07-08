package com.baeldung.spring.cloud.aws.sqs.conversion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.baeldung.spring.cloud.aws.sqs.conversion.configuration.ShipmentEventsQueuesProperties;

@SpringBootApplication
public class ShipmentServiceApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ShipmentServiceApplication.class);
        app.setAdditionalProfiles("shipping");
        app.run(args);
    }

}
