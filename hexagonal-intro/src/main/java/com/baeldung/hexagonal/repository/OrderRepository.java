package com.baeldung.hexagonal.repository;

import com.baeldung.hexagonal.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository {
    void save(Order order);
}
