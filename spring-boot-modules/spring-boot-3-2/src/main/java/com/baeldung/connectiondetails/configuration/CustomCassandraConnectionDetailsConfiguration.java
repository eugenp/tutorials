package com.baeldung.connectiondetails.configuration;

import org.springframework.boot.autoconfigure.cassandra.CassandraConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
@Profile("cassandra")
public class CustomCassandraConnectionDetailsConfiguration {
    @Bean
    @Primary
    public CassandraConnectionDetails getCustomCassandraConnectionDetails() {
        return new CustomCassandraConnectionDetails();
    }
}
