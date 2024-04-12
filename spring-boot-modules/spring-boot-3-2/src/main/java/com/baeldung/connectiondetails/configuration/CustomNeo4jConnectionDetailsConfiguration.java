package com.baeldung.connectiondetails.configuration;

import org.springframework.boot.autoconfigure.neo4j.Neo4jConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration(proxyBeanMethods = false)
@Profile("neo4j")
public class CustomNeo4jConnectionDetailsConfiguration {
    @Bean
    @Primary
    public Neo4jConnectionDetails getNeo4jConnectionDetails() {
        return new CustomNeo4jConnectionDetails();
    }
}
