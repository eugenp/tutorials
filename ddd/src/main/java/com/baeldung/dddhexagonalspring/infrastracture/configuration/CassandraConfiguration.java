package com.baeldung.dddhexagonalspring.infrastracture.configuration;

import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.baeldung.dddhexagonalspring.infrastracture.repository.cassandra.SpringDataCassandraOrderRepository;

@EnableCassandraRepositories(basePackageClasses = SpringDataCassandraOrderRepository.class)
public class CassandraConfiguration {

}
