package com.baeldung.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final JdbcTemplate jdbcTemplate;

    public OrderService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void placeOrder(long orderId, long productId) {
        jdbcTemplate.update(
                "insert into orders(id, product_id) values (?, ?)",
                orderId,
                productId
        );

        jdbcTemplate.update(
                "update products set stock = stock - 1 where id = ?",
                productId
        );
    }
}
