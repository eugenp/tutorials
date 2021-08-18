package com.hexagon.hexagon_architecture.Framework.Config;

import com.hexagon.hexagon_architecture.Framework.Repository.UserMongoRepository;

import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = UserMongoRepository.class)
public class MongoDBConfig {
    
}
