package com.baeldung.h2functions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompiledFunctionUnitTest {
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
    void givenAUserDefinedFunctionAsClassReference_whenICallTheFunction_thenIGetTheResult() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                CREATE ALIAS JAVA_RANDOM FOR "java.lang.Math.random";
                """);
        }

        try (PreparedStatement statement = connection.prepareStatement("SELECT JAVA_RANDOM()")) {
            ResultSet rs = statement.executeQuery();

            rs.next();
            double rnd = rs.getDouble(1);
            assertTrue(rnd >= 0);
            assertTrue(rnd <= 1);
        }
    }

    @Test
    void givenAUserDefinedFunctionAsMyOwnClassReference_whenICallTheFunction_thenIGetTheResult() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                CREATE ALIAS HELLO FOR "com.baeldung.h2functions.CompiledFunctionUnitTest.hello";
                """);
        }

        try (PreparedStatement statement = connection.prepareStatement("SELECT HELLO()")) {
            ResultSet rs = statement.executeQuery();

            rs.next();
            String hello = rs.getString(1);
            assertEquals("Hello", hello);
        }
    }

    public static String hello() {
        return "Hello";
    }
}
