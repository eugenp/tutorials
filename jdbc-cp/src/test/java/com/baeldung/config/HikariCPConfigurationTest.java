package com.baeldung.config;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariDataSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class HikariCPConfigurationTest {

    private static HikariDataSource dataSource;

    @BeforeAll
    public static void setUp() {
        dataSource = HikariCPConfiguration.configureDataSource();
    }

    @AfterAll
    public static void tearDown() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

    @Test
    public void testDataSourceNotNull() {
        assertNotNull(dataSource, "DataSource should not be null");
    }

    @Test
    public void testConnection() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection, "Connection should not be null");
            assertEquals(Connection.TRANSACTION_READ_COMMITTED, connection.getTransactionIsolation());
        }
    }

    @Test
    public void testConfigurationSettings() {
        assertEquals(10, dataSource.getMaximumPoolSize());
        assertEquals(2, dataSource.getMinimumIdle());
        assertEquals(30000, dataSource.getConnectionTimeout());
        assertEquals(600000, dataSource.getIdleTimeout());
    }
}