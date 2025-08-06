package com.baeldung.h2functions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuiltInFunctionUnitTest {
    private static Connection connection;

    @BeforeEach
    public void setUp() throws Exception {
        connection = DriverManager.getConnection("jdbc:h2:mem:generated", "sa", "");
    }

    @AfterEach
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void whenUsingUpper_thenParametersAreUppercased() throws SQLException{
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery("SELECT UPPER('Hello')");

            rs.next();
            String hello = rs.getString(1);
            assertEquals("HELLO", hello);
        }
    }
}
