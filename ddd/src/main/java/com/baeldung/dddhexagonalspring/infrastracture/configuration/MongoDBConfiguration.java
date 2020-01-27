package com.baeldung.dddhexagonalspring.infrastracture.configuration;

import com.baeldung.dddhexagonalspring.infrastracture.repository.SpringDataMongoOrderRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = SpringDataMongoOrderRepository.class)
public class MongoDBConfiguration {
}
