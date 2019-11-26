package com.baeldung.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.domain.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {
    
    
    Optional<Order> findById(Integer id);

}
