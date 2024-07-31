package com.baeldung.connectiondetails.configuration;

import org.springframework.boot.autoconfigure.mongo.MongoConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
@Profile("mongo")
public class MongoDBConnectionDetailsConfiguration {
    @Bean
    @Primary
    public MongoConnectionDetails getMongoConnectionDetails() {
        return new MongoDBConnectionDetails();
    }
}
