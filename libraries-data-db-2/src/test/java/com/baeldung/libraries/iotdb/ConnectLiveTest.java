package com.baeldung.libraries.iotdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ConnectLiveTest {
    private static final String IOTDB_URL = "jdbc:iotdb://127.0.0.1:6667/";
    private static final String IOTDB_USERNAME = "root";
    private static final String IOTDB_PASSWORD = "root";

    @BeforeAll
    static void loadDrivers() throws ClassNotFoundException {
        Class.forName("org.apache.iotdb.jdbc.IoTDBDriver");
    }

    @Test
    void whenConnectingToIotdb_thenTheConnectionIsSuccessful() throws SQLException {
        try (Connection con = DriverManager.getConnection(IOTDB_URL, IOTDB_USERNAME, IOTDB_PASSWORD)) {
            // use conn here
        }
    }
}
