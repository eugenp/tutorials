package com.baeldung.aspect.transactional.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.aspect.transactional.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
