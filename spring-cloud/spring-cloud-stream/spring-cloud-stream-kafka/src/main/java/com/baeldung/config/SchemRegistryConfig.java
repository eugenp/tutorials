package com.baeldung.config;

import org.springframework.cloud.stream.schema.client.ConfluentSchemaRegistryClient;
import org.springframework.cloud.stream.schema.client.SchemaRegistryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchemRegistryConfig {

    @Bean
    public SchemaRegistryClient schemaRegistryClient() {
        ConfluentSchemaRegistryClient client = new ConfluentSchemaRegistryClient();
        client.setEndpoint("http://localhost:8081");
        return client;
    }
}
