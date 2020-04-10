package com.baeldung.dddhexagonalspring.infrastracture.configuration;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.baeldung.dddhexagonalspring.infrastracture.repository.mongo.SpringDataMongoOrderRepository;

@EnableMongoRepositories(basePackageClasses = SpringDataMongoOrderRepository.class)
public class MongoDBConfiguration {
}
