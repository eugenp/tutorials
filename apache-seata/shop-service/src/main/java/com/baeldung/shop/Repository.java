package com.baeldung.shop;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class Repository {
    private static final Logger LOG = LoggerFactory.getLogger(Repository.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void updateDatabase() {
        var params = Map.of(
            "id", UUID.randomUUID().toString(),
            "created", Instant.now().atOffset(ZoneOffset.UTC)
        );

        LOG.info("Updating database with {}", params);

        int result = jdbcTemplate.update("INSERT INTO shop_table(id, created) VALUES (:id, :created)",
            params);

        LOG.info("Updating database with result {}", result);
    }
}
