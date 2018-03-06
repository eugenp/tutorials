package com.insightsource.pool.jdbc;

import com.insightsource.pool.ObjectFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionFactory implements ObjectFactory<Connection> {
    private String url;
    private String name;
    private String password;

    public JDBCConnectionFactory(String driver, String url, String name, String password) {
        super();

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to find driver in classpath", e);
        }

        this.url = url;
        this.name = name;
        this.password = password;
    }

    public Connection createNew() {
        try {
            return DriverManager.getConnection(url, name, password);
        } catch (SQLException e) {
            throw new IllegalArgumentException("Unable to create new connection", e);
        }
    }

}
