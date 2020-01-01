package com.baeldung.dddhexagonalspring.infrastracture.configuration;

import com.baeldung.dddhexagonalspring.infrastracture.repository.SpringDataOrderRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = SpringDataOrderRepository.class)
public class MongoDBConfiguration {
}
