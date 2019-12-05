package com.baeldung.hexagonal.boundary.output;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.core.entities.Order;

@Repository public interface OrderRepository extends CrudRepository<Order, Long>{
}
