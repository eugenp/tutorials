package com.baeldung.quarkus.utils;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

public class PostgreSqlContainerResource implements QuarkusTestResourceLifecycleManager {

    private static final PostgreSQLContainer CONTAINER = new PostgreSQLContainer<>();

    @Override
    public Map<String, String> start() {
        CONTAINER.start();

        Map<String, String> map = new HashMap<>();
        map.put("%test-postgre.quarkus.datasource.username", CONTAINER.getUsername());
        map.put("%test-postgre.quarkus.datasource.password", CONTAINER.getPassword());
        map.put("%test-postgre.quarkus.datasource.jdbc.url", CONTAINER.getJdbcUrl());
        return map;
    }

    @Override
    public void stop() {
        CONTAINER.stop();
    }

}
