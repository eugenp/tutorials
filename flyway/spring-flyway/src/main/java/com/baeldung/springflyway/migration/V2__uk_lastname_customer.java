package com.baeldung.springflyway.migration;

import org.flywaydb.core.api.migration.spring.SpringJdbcMigration;
import org.springframework.jdbc.core.JdbcTemplate;

public class V2__uk_lastname_customer implements SpringJdbcMigration {

    final String CUSTOMER_LASTNAME_UK = "ALTER TABLE customer  ADD CONSTRAINT uk_customer_lastname UNIQUE(last_name);";

    @Override
    public void migrate(final JdbcTemplate jdbcTemplate) throws Exception {
        jdbcTemplate.execute(CUSTOMER_LASTNAME_UK);
    }
}
