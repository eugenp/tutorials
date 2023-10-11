package com.baeldung.connectiondetails.configuration;

import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
@Profile("rabbitmq")
public class RabbitMQConnectionDetailsConfiguration {
    @Primary
    @Bean
    public RabbitConnectionDetails getRabbitmqConnection() {
        return new RabbitMQConnectionDetails();
    }
}
