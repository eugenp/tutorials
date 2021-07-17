package com.baeldung.pattern.hexagonal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.baeldung.pattern.hexagonal.persistence.MongoBookRepo;
import com.baeldung.pattern.hexagonal.persistence.MongoRepoEx;

@Configuration
@EnableMongoRepositories(basePackageClasses = { MongoRepoEx.class, MongoBookRepo.class })
public class MongoConfig {

}
