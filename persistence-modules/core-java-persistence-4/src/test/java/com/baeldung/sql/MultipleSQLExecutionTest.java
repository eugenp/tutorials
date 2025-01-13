package com.baeldung.sql;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultipleSQLExecutionTest {

    private Connection connection;

    @BeforeEach
    public void setupConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_db", "username", "password");
    }

    @AfterEach
    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    public void whenExecutingMultipleStatements_thenRecordsAreInserted() throws SQLException {
        
        MultipleSQLExecution execution = new MultipleSQLExecution(connection);
        boolean result = execution.executeMultipleStatements();
        assertTrue(result, "The statements should execute successfully.");

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS count FROM users WHERE name IN ('Alice', 'Bob')")) {
            resultSet.next();
            int count = resultSet.getInt("count");
            assertEquals(2, count, "Two records should have been inserted.");
        }
    }

    @Test
    public void whenExecutingBatchProcessing_thenRecordsAreInserted() throws SQLException {
        
        MultipleSQLExecution execution = new MultipleSQLExecution(connection);
        int[] updateCounts = execution.executeBatchProcessing();
        assertEquals(2, updateCounts.length, "Batch processing should execute two statements.");

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS count FROM users WHERE name IN ('Charlie', 'Diana')")) {
            resultSet.next();
            int count = resultSet.getInt("count");
            assertEquals(2, count, "Two records should have been inserted via batch.");
        }
    }

    @Test
    public void whenCallingStoredProcedure_thenRecordsAreInserted() throws SQLException {
        
        MultipleSQLExecution execution = new MultipleSQLExecution(connection);
        boolean result = execution.callStoredProcedure();
        assertTrue(result, "The stored procedure should execute successfully.");

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS count FROM users WHERE name IN ('Eve', 'Frank')")) {
            resultSet.next();
            int count = resultSet.getInt("count");
            assertEquals(2, count, "Stored procedure should have inserted two records.");
        }
    }
}

