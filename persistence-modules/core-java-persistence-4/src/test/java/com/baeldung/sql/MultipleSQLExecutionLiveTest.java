package com.baeldung.sql;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Please note, this test requires a MySQL server running on localhost:3306 with database users_db already created.
// We have added creation of the table it in the setup() method.
public class MultipleSQLExecutionLiveTest {

    private static Connection connection;
    
    @Before
    public void setup() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/user_db?allowMultiQueries=true";
        String username = "username";
        String password = "password";
        connection = DriverManager.getConnection(url, username, password);
            
        Statement statement = connection.createStatement();
        String createUsersSql = "CREATE TABLE users ( id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50) NOT NULL, email VARCHAR(100) NOT NULL UNIQUE );";
        statement.execute(createUsersSql);
    }

    @Test
    public void givenMultipleStatements_whenExecuting_thenRecordsAreInserted() throws SQLException {       
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
    public void givenBatchProcessing_whenExecuting_thenRecordsAreInserted() throws SQLException {
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
    public void givenStoredProcedure_whenCalling_thenRecordsAreInserted() throws SQLException {
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
    
    @Test
    public void givenMultipleSelectStatements_whenExecuting_thenCorrectUsersAreFetched() throws SQLException {
        MultipleSQLExecution execution = new MultipleSQLExecution(connection);
        execution.executeMultipleStatements();

        List<User> users = execution.executeMultipleSelectStatements();

        // Here we verify the correct number of users were fetched
        assertEquals(2, users.size(), "There should be exactly two users fetched.");

        List<String> fetchedUserNames = users.stream()
                                             .map(User::getName)
                                             .toList();

        // Here, we verify that expected users are present
        List<String> expectedUserNames = List.of("Alice", "Bob");
        assertTrue(fetchedUserNames.containsAll(expectedUserNames), "Fetched users should match the expected names.");
    }
}

