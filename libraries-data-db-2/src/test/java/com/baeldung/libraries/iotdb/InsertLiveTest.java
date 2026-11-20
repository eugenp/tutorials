package com.baeldung.libraries.iotdb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class InsertLiveTest {
    private static final String IOTDB_URL = "jdbc:iotdb://127.0.0.1:6667/";
    private static final String IOTDB_USERNAME = "root";
    private static final String IOTDB_PASSWORD = "root";

    @BeforeAll
    static void loadDrivers() throws ClassNotFoundException {
        Class.forName("org.apache.iotdb.jdbc.IoTDBDriver");
    }

    @Test
    void whenInsertingData_thenDataIsInserted() throws SQLException {
        try (Connection con = DriverManager.getConnection(IOTDB_URL, IOTDB_USERNAME, IOTDB_PASSWORD)) {
            try (PreparedStatement stmt = con.prepareStatement("INSERT INTO root.baeldung.turbine.device1(timestamp, speed) VALUES (?, ?)")) {
                stmt.setObject(1, Instant.now().toEpochMilli());
                stmt.setObject(2, 10);
                int count = stmt.executeUpdate();
                assertEquals(0, count); // No row count returned by IoTDB
            }
        }
    }

    @Test
    void whenInsertingDataWithoutTimestamp_thenDataIsInserted() throws SQLException {
        try (Connection con = DriverManager.getConnection(IOTDB_URL, IOTDB_USERNAME, IOTDB_PASSWORD)) {
            try (PreparedStatement stmt = con.prepareStatement("INSERT INTO root.baeldung.turbine.device1(speed) VALUES (?)")) {
                stmt.setObject(1, 20);
                int count = stmt.executeUpdate();
                assertEquals(0, count); // No row count returned by IoTDB
            }
        }
    }

    @Test
    void whenInsertingDataIntoAlignedTimeseries_thenDataIsInserted() throws SQLException {
        try (Connection con = DriverManager.getConnection(IOTDB_URL, IOTDB_USERNAME, IOTDB_PASSWORD)) {
            try (PreparedStatement stmt = con.prepareStatement("INSERT INTO root.baeldung.car.device2(lat, lng) VALUES (?, ?)")) {
                stmt.setObject(1, 40.6892);
                stmt.setObject(2, 74.0445);
                int count = stmt.executeUpdate();
                assertEquals(0, count); // No row count returned by IoTDB
            }
        }
    }
}
