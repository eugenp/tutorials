package com.baeldung;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * A JUnit 5 extension responsible for setting up a Postgres instance before all
 * tests and tearing it down after them.
 */
public class PostgresExtension implements BeforeAllCallback, AfterAllCallback {

    private PostgreSQLContainer<?> postgres;

    @Override
    public void beforeAll(ExtensionContext context) {
        postgres = new PostgreSQLContainer<>("postgres:11.1")
                .withDatabaseName("baeldung")
                .withUsername("test")
                .withExposedPorts(5432)
                .withPassword("test");
        postgres.start();
        System.setProperty("TEST_PG_PORT", postgres.getFirstMappedPort() + "");
    }

    @Override
    public void afterAll(ExtensionContext context) {
        postgres.stop();
        System.clearProperty("TEST_PG_PORT");
    }
}
