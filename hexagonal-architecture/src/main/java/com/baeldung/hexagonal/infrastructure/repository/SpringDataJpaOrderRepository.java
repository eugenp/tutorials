package com.baeldung.hexagonal.infrastructure.repository;

import org.springframework.data.repository.CrudRepository;

public interface SpringDataJpaOrderRepository extends CrudRepository<OrderEntity, Long> {
}
