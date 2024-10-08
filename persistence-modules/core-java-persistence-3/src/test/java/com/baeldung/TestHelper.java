package com.baeldung;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestHelper {

    protected static Connection connection = null;

    protected static Connection connect(String url, String user, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        if (connection != null) {
            System.out.println("Connected to database");
        }

        return connection;
    }

    protected static void populateDB() throws SQLException {
        createEmployeeTable();
    }

    private static void createEmployeeTable() throws SQLException {
        String createTable = """
            CREATE TABLE EMPLOYEES (
                id SERIAL PRIMARY KEY ,
                first_name VARCHAR(50),
                last_name VARCHAR(50),
                salary DECIMAL(10, 2)
            );
            """;
        PreparedStatement preparedStatement = connection.prepareStatement(createTable);
        preparedStatement.execute();
    }

    protected static void destroyDB() throws SQLException {
        String destroy = """
              DROP table IF EXISTS EMPLOYEES;
            """;
        connection.prepareStatement(destroy)
            .execute();
    }
}
