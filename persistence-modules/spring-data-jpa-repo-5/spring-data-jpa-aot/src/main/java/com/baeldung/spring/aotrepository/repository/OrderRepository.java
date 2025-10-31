package com.baeldung.spring.aotrepository.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.spring.aotrepository.entity.Order;

public interface OrderRepository extends Repository<Order, Long> {

    Order save(Order order);

    @Transactional(readOnly = true)
    List<Order> findAll();

    List<Order> findAllById(Iterable<Long> longs);

    @Query(value = "SELECT * FROM ORDERS", nativeQuery = true)
    List<Order> nativeQueryFindAllOrders();

    @Query(value = "SELECT u FROM Order u")
    List<Order> queryFindAllOrders();
}
