package com.baeldung.pattern.hexagonal.config;

import com.baeldung.pattern.hexagonal.persistence.MongoRepoEx;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackageClasses = MongoRepoEx.class)
public class MongoConfig {
}
