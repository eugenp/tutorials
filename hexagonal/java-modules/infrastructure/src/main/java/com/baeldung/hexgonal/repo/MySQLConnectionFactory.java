package com.baeldung.hexgonal.repo;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
public class MySQLConnectionFactory {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/employeeDB?serverTimezone=UTC";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD);
        } catch (SQLException e) {
            log.error("Error connecting to database", e);
        }
        return connection;
    }

}
