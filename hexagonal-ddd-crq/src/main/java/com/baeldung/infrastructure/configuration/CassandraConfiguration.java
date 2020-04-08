package com.baeldung.infrastructure.configuration;

import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.baeldung.infrastructure.repository.SpringDataCassandraChgRequestRepository;

@EnableCassandraRepositories(basePackageClasses = SpringDataCassandraChgRequestRepository.class)
public class CassandraConfiguration {
}
