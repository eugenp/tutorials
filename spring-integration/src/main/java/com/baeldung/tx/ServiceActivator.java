package com.baeldung.tx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class ServiceActivator {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void checkTestResults(String payload) {

        this.jdbcTemplate.update("insert into STUDENT values(?)", payload);

        if (payload.toLowerCase().startsWith("fail")) {
            log.error("Service failure. Test result: {}", payload);
            throw new RuntimeException("Service failure.");
        }

        log.info("Service success. Test result: {}", payload);
    }

}
