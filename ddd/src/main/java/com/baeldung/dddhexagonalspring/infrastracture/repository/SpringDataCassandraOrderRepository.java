package com.baeldung.dddhexagonalspring.infrastracture.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.baeldung.dddhexagonalspring.domain.Order;

public interface SpringDataCassandraOrderRepository extends CassandraRepository<Order, UUID>{

}
