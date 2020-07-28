package com.baeldung.architecture.hexagonal.repositories.configuration;

import com.baeldung.architecture.hexagonal.repositories.mondodb.SpringDataMongoQuoteRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = SpringDataMongoQuoteRepository.class)
public class MongoDbConfiguration {
}
