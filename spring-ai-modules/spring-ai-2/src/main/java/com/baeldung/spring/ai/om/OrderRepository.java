package com.baeldung.spring.ai.om;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderInfo, Long> {
    @Override
    OrderInfo save(OrderInfo entity);
    Optional<List<OrderInfo>> findByUserID(String userID);
}
