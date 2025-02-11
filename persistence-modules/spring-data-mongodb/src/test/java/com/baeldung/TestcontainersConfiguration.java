package com.baeldung;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistrar;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {

    @Bean
    MongoDBContainer mongoDbContainer() {
        return new MongoDBContainer(DockerImageName.parse("mongo:8"));
    }

    @Bean
    DynamicPropertyRegistrar dynamicPropertyRegistrar(MongoDBContainer mongoDbContainer) {
        return registry -> {
            registry.add("spring.data.mongodb.uri", mongoDbContainer::getConnectionString);
            registry.add("spring.data.mongodb.database", () -> "technical-content-management");
        };
    }

}