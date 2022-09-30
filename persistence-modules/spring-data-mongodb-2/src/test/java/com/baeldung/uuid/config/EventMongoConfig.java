package com.baeldung.uuid.config;

import org.bson.UuidRepresentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.baeldung.uuid.event.UuidIdentifiedEntityEventListener;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoRepositories(basePackages = "com.baeldung.uuid.repository")
public class EventMongoConfig {

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
    public UuidIdentifiedEntityEventListener uuidIdentifiedEntityEventListener() {
  
        return new UuidIdentifiedEntityEventListener();
    }
}
