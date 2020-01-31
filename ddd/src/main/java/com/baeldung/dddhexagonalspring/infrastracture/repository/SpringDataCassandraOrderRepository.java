package com.baeldung.dddhexagonalspring.infrastracture.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.dddhexagonalspring.domain.Order;

@Repository
public interface SpringDataCassandraOrderRepository extends CassandraRepository<OrderEntity, UUID>{

}
