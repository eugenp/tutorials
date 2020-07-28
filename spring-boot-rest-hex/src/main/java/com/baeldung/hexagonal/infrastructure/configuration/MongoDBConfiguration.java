package com.baeldung.hexagonal.infrastructure.configuration;

import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.baeldung.hexagonal.infrastructure.repository.mongo.LibraryRepositorySpringDataMongoDb;

@EnableCassandraRepositories(basePackageClasses = LibraryRepositorySpringDataMongoDb.class)
public class MongoDBConfiguration {

}