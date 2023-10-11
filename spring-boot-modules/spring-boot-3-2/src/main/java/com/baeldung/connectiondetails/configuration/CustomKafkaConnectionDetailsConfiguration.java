package com.baeldung.connectiondetails.configuration;

import org.springframework.boot.autoconfigure.kafka.KafkaConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
@Profile("kafka")
public class CustomKafkaConnectionDetailsConfiguration {
    @Bean
    public KafkaConnectionDetails getKafkaConnectionDetails() {
        return new CustomKafkaConnectionDetails();
    }
}
