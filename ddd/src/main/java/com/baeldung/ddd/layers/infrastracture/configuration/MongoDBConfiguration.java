package com.baeldung.ddd.layers.infrastracture.configuration;

import com.baeldung.ddd.layers.infrastracture.repository.SpringDataOrderRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = SpringDataOrderRepository.class)
public class MongoDBConfiguration {
}
