package com.baeldung.messaging.postgresql.repository;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.messaging.postgresql.domain.Order;

public interface OrdersRepository extends CrudRepository<Order, Long>{

}
