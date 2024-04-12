package com.baeldung.axon.querymodel;

import com.mongodb.client.MongoClient;

import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.Serializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("mongo")
public class MongoConfiguration {

    @Bean
    public TokenStore getTokenStore(MongoClient client, Serializer serializer) {
        return MongoTokenStore.builder()
          .mongoTemplate(DefaultMongoTemplate.builder()
            .mongoDatabase(client)
            .build())
          .serializer(serializer)
          .build();
    }
}
