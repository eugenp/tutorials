package com.baeldung.recordswithjpa.repository;

import com.baeldung.recordswithjpa.records.CustomBookRecord;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomBookRepositoryImpl implements CustomBookRepository {
    private final JdbcTemplate jdbcTemplate;

    public CustomBookRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<CustomBookRecord> findAllBooks() {
        return jdbcTemplate.query("SELECT id, title FROM book", (rs, rowNum) -> new CustomBookRecord(rs.getLong("id"), rs.getString("title")));
    }
}
