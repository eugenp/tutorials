package com.baeldung.tableexists;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DatabaseConfig {
    static Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        String url = "jdbc:h2:mem:testdb";
        return DriverManager.getConnection(url, "user", "password");
    }

    static void createTables(Connection connection) throws SQLException {
        connection.createStatement().executeUpdate("create table EMPLOYEE (id int primary key auto_increment, name VARCHAR(255))");
    }

    static void dropTables(Connection connection) throws SQLException {
        connection.createStatement().executeUpdate("drop table EMPLOYEE");
    }
}
