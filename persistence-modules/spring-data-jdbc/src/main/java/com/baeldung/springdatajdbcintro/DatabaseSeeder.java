package com.baeldung.springdatajdbcintro;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.Logger;

@Component
public class DatabaseSeeder {

    private static final Logger LOGGER = (Logger) LoggerFactory.getLogger(DatabaseSeeder.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertData() {
        LOGGER.info("> Inserting data...");
        jdbcTemplate.execute("INSERT INTO Person(first_name,last_name) VALUES('Victor', 'Hygo')");
        jdbcTemplate.execute("INSERT INTO Person(first_name,last_name) VALUES('Dante', 'Alighieri')");
        jdbcTemplate.execute("INSERT INTO Person(first_name,last_name) VALUES('Stefan', 'Zweig')");
        jdbcTemplate.execute("INSERT INTO Person(first_name,last_name) VALUES('Oscar', 'Wilde')");
    }

}
