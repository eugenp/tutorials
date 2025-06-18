package com.baeldung.quarkus;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

public class PostgresTestResource implements QuarkusTestResourceLifecycleManager {

    private PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test_db")
            .withUsername("test_user")
            .withPassword("test_password");

    @Override
    public Map<String, String> start() {
        postgres.start();

        Map<String, String> config = new HashMap<>();
        config.put("quarkus.datasource.jdbc.url", postgres.getJdbcUrl());
        config.put("quarkus.datasource.username", postgres.getUsername());
        config.put("quarkus.datasource.password", postgres.getPassword());
        config.put("quarkus.datasource.db-kind", "postgresql");
        config.put("quarkus.hibernate-orm.database.generation", "drop-and-create");

        return config;
    }

    @Override
    public void stop() {
        if (postgres != null) {
            postgres.stop();
        }
    }
}