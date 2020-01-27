package com.baeldung.dddhexagonalspring.infrastracture.configuration;

import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.baeldung.dddhexagonalspring.infrastracture.repository.SpringDataCassandraOrderRepository;

@EnableCassandraRepositories(basePackageClasses = SpringDataCassandraOrderRepository.class)
public class CassandraConfiguration {

}
