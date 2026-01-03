package com.baeldung.libraries.iotdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SelectLiveTest {
    private static final String IOTDB_URL = "jdbc:iotdb://127.0.0.1:6667/";
    private static final String IOTDB_USERNAME = "root";
    private static final String IOTDB_PASSWORD = "root";

    private static final Instant NOW = Instant.parse("2025-12-30T07:49:15.000Z");

    @BeforeAll
    static void loadDrivers() throws ClassNotFoundException {
        Class.forName("org.apache.iotdb.jdbc.IoTDBDriver");
    }

    @BeforeEach
    void setupData() throws SQLException {
        try (Connection con = DriverManager.getConnection(IOTDB_URL, IOTDB_USERNAME, IOTDB_PASSWORD)) {
            try (Statement stmt = con.createStatement()) {
                stmt.executeUpdate("DELETE FROM root.baeldung.turbine.device1.speed");
                stmt.executeUpdate("DELETE FROM root.baeldung.car.device2.lat");
                stmt.executeUpdate("DELETE FROM root.baeldung.car.device2.lng");
            }

            try (PreparedStatement stmt = con.prepareStatement("INSERT INTO root.baeldung.turbine.device1(timestamp, speed) VALUES (?, ?)")) {
                stmt.setObject(1, NOW.toEpochMilli());
                stmt.setObject(2, 10);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = con.prepareStatement("INSERT INTO root.baeldung.car.device2(timestamp, lat, lng) VALUES (?, ?, ?)")) {
                stmt.setObject(1, NOW.toEpochMilli());
                stmt.setObject(2, 40.6892);
                stmt.setObject(3, 74.0445);
                stmt.executeUpdate();
            }

        }
    }

    @Test
    void whenSelectingAllRows_thenRowsAreReturned() throws SQLException {
        try (Connection con = DriverManager.getConnection(IOTDB_URL, IOTDB_USERNAME, IOTDB_PASSWORD)) {
            try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM root.baeldung.turbine.device1")) {
                try (ResultSet rs = stmt.executeQuery()) {
                    // Should be a first row
                    assertTrue(rs.next());
                    assertEquals(NOW.toEpochMilli(), rs.getLong(1));
                    assertEquals(10, rs.getFloat(2));

                    // Should be no second row
                    assertFalse(rs.next());
                }
            }
        }
    }

    @Test
    void whenSelectingSingleRow_thenRowsAreReturned() throws SQLException {
        try (Connection con = DriverManager.getConnection(IOTDB_URL, IOTDB_USERNAME, IOTDB_PASSWORD)) {
            try (PreparedStatement stmt = con.prepareStatement("SELECT lat FROM root.baeldung.car.device2 WHERE timestamp = ?")) {
                stmt.setObject(1, NOW.toEpochMilli());

                try (ResultSet rs = stmt.executeQuery()) {
                    // Should be a first row
                    assertTrue(rs.next());
                    assertEquals(NOW.toEpochMilli(), rs.getLong(1));
                    assertEquals(40.6892, rs.getFloat(2), 0.1);

                    // Should be no second row
                    assertFalse(rs.next());
                }
            }
        }
    }
}
