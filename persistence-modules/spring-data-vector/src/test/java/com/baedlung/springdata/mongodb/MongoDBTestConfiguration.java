package com.baedlung.springdata.mongodb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.testcontainers.mongodb.MongoDBAtlasLocalContainer;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@Profile("mongodb")
public class MongoDBTestConfiguration {

    @Bean
    public MongoDBAtlasLocalContainer mongoDBAtlasLocalContainer() {
        MongoDBAtlasLocalContainer mongoDBAtlasLocalContainer =
            new MongoDBAtlasLocalContainer("mongodb/mongodb-atlas-local:7.0.9");
        mongoDBAtlasLocalContainer.start();
        return mongoDBAtlasLocalContainer;
    }

    @Bean
    @DependsOn("mongoDBAtlasLocalContainer")
    public MongoClientFactoryBean mongo(MongoDBAtlasLocalContainer container) {
        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
        mongo.setHost(container.getHost());
        return mongo;
    }

    @Bean
    @DependsOn("mongoDBAtlasLocalContainer")
    public MongoClient mongoClient(MongoDBAtlasLocalContainer container) {
        return MongoClients.create(container.getConnectionString());
    }

    @Bean
    @DependsOn("mongoClient")
    public MongoOperations mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "geospatial");
    }
}
