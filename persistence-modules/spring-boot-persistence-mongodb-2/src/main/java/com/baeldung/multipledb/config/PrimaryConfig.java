package com.baeldung.multipledb.config;

import static java.util.Collections.singletonList;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.baeldung.multipledb.repository.primary.UserRepository;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
@EnableMongoRepositories(basePackageClasses = UserRepository.class, mongoTemplateRef = "primaryMongoTemplate")
@EnableConfigurationProperties
public class PrimaryConfig {

    @Bean(name = "primaryProperties")
    @ConfigurationProperties(prefix = "mongodb.primary")
    @Primary
    public MongoProperties primaryProperties() {
        return new MongoProperties();
    }

    @Bean(name = "primaryMongoClient")
    public MongoClient mongoClient(@Qualifier("primaryProperties") MongoProperties mongoProperties) {

        MongoCredential credential = MongoCredential.createCredential(mongoProperties.getUsername(), mongoProperties.getAuthenticationDatabase(), mongoProperties.getPassword());

        return MongoClients.create(MongoClientSettings.builder()
          .applyToClusterSettings(builder -> builder.hosts(singletonList(new ServerAddress(mongoProperties.getHost(), mongoProperties.getPort()))))
          .credential(credential)
          .build());
    }

    @Primary
    @Bean(name = "primaryMongoDBFactory")
    public MongoDatabaseFactory mongoDatabaseFactory(@Qualifier("primaryMongoClient") MongoClient mongoClient, @Qualifier("primaryProperties") MongoProperties mongoProperties) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, mongoProperties.getDatabase());
    }

    @Primary
    @Bean(name = "primaryMongoTemplate")
    public MongoTemplate mongoTemplate(@Qualifier("primaryMongoDBFactory") MongoDatabaseFactory mongoDatabaseFactory) {
        return new MongoTemplate(mongoDatabaseFactory);
    }
}
