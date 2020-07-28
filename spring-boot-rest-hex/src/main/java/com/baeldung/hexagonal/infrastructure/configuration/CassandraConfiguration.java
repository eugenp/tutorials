package com.baeldung.hexagonal.infrastructure.configuration;

import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.baeldung.hexagonal.infrastructure.repository.cassandra.LibraryRepositorySpringDataCassandra;

@EnableCassandraRepositories(basePackageClasses = LibraryRepositorySpringDataCassandra.class)
public class CassandraConfiguration {

}