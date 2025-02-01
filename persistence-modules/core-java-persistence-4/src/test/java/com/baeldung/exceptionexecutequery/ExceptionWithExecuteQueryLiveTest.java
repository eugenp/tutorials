package com.baeldung.exceptionexecutequery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ExceptionWithExecuteQueryLiveTest {

    private static final String USERNAME = "johndoe";
    private static final String EMAIl = "johndoe@example.com";

    private static Connection connection;

    @BeforeAll
    public static void init() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost/sample_db?" + "user=root&password=13042014");

        connection.createStatement()
            .execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    username VARCHAR(50),
                    email VARCHAR(50)
                )
                """);
    }

    @AfterAll
    public static void shutdown() throws SQLException {
        connection.createStatement()
            .execute("DROP TABLE users");
        connection.close();
    }

    @Test
    void givenInsertSql_whenExecuteUpdate_thenAssertUserSaved() throws SQLException {
        String insertSql = "INSERT INTO users (username, email) VALUES (?, ?)";
        PreparedStatement insertStatement = connection.prepareStatement(insertSql);
        insertStatement.setString(1, USERNAME);
        insertStatement.setString(2, EMAIl);
        insertStatement.executeUpdate();

        String selectSql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement selectStatement = connection.prepareStatement(selectSql);
        selectStatement.setString(1, USERNAME);
        ResultSet resultSet = selectStatement.executeQuery();

        resultSet.next();
        assertEquals(USERNAME, resultSet.getString("username"));
        assertEquals(EMAIl, resultSet.getString("email"));
    }

    @Test
    void givenInsertSql_whenExecuteQuery_thenAssertSqlExceptionThrown() throws SQLException {
        String insertSql = "INSERT INTO users (username, email) VALUES (?, ?)";
        PreparedStatement insertStatement = connection.prepareStatement(insertSql);
        insertStatement.setString(1, USERNAME);
        insertStatement.setString(2, EMAIl);

        SQLException exception = assertThrows(SQLException.class, insertStatement::executeQuery);
        assertEquals("Statement.executeQuery() cannot issue statements that do not produce result sets.", exception.getMessage());
    }

    @Test
    void givenUpdateSql_whenExecuteUpdate_thenAssertUserSaved() throws SQLException {
        String insertSql = "INSERT INTO users (username, email) VALUES (?, ?)";
        PreparedStatement insertStatement = connection.prepareStatement(insertSql);
        insertStatement.setString(1, USERNAME);
        insertStatement.setString(2, EMAIl);
        insertStatement.executeUpdate();

        String newEmail = "johndoe@baeldung.com";
        String updateSql = "UPDATE users SET email = ? WHERE username = ?";
        PreparedStatement updateStatement = connection.prepareStatement(updateSql);
        updateStatement.setString(1, newEmail);
        updateStatement.setString(2, USERNAME);
        updateStatement.executeUpdate();

        String selectSql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement selectStatement = connection.prepareStatement(selectSql);
        selectStatement.setString(1, USERNAME);
        ResultSet resultSet = selectStatement.executeQuery();

        resultSet.next();
        assertEquals(USERNAME, resultSet.getString("username"));
        assertEquals(newEmail, resultSet.getString("email"));
    }

    @Test
    void givenUpdateSql_whenExecuteQuery_thenAssertSqlExceptionThrown() throws SQLException {
        String insertSql = "INSERT INTO users (username, email) VALUES (?, ?)";
        PreparedStatement insertStatement = connection.prepareStatement(insertSql);
        insertStatement.setString(1, USERNAME);
        insertStatement.setString(2, EMAIl);
        insertStatement.executeUpdate();

        String newEmail = "johndoe@baeldung.com";
        String updateSql = "UPDATE users SET email = ? WHERE username = ?";
        PreparedStatement updateStatement = connection.prepareStatement(updateSql);
        updateStatement.setString(1, newEmail);
        updateStatement.setString(2, USERNAME);
        assertThrows(SQLException.class, updateStatement::executeQuery);
    }

}
