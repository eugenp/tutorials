package com.example.hexagonalarch.cnfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DbCnfg {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void configure() {
        jdbcTemplate.execute("DROP TABLE user IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE user(" + "id SERIAL, name VARCHAR(50), age NUMBER)");
    }
}
