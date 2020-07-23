package com.baeldung.hexagonal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.baeldung.hexagonal.infrastructure.adapter.mongodb.repository.BookMongoDbRepository;

@Configuration
@EnableMongoRepositories(basePackageClasses = BookMongoDbRepository.class)
public class MongoConfig {
}
