package com.baeldung.uuid.config;

import java.util.UUID;

import org.bson.UuidRepresentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.baeldung.uuid.model.UuidIdentifiedEntity;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoRepositories(basePackages = "com.baeldung.uuid.repository")
public class EntityCallbackMongoConfig {

    @Bean
    public MongoClient mongo() throws Exception {
        final ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/test");
        final MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                                                                    .uuidRepresentation(UuidRepresentation.STANDARD)
                                                                    .applyConnectionString(connectionString).build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "test");
    }
    
    @Bean
    public BeforeConvertCallback<UuidIdentifiedEntity> beforeSaveCallback() {
        
        return (entity, collection) -> {
          
            if(entity.getId() == null) {
                entity.setId(UUID.randomUUID());
            }
            return entity;
        };        
    }
    
}
