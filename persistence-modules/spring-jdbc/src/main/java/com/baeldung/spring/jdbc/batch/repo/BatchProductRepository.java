package com.baeldung.spring.jdbc.batch.repo;

import com.baeldung.spring.jdbc.batch.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class BatchProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public BatchProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void saveAll(List<Product> products) {
        jdbcTemplate.batchUpdate("INSERT INTO PRODUCT (TITLE, CREATED_TS, PRICE) VALUES (?, ?, ?)",
          products,
          100,
          (PreparedStatement ps, Product product) -> {
            ps.setString(1, product.getTitle());
            ps.setTimestamp(2, Timestamp.valueOf(product.getCreatedTs()));
            ps.setBigDecimal(3, product.getPrice());
        });
    }
}
