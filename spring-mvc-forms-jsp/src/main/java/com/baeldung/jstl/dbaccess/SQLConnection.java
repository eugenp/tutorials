package com.baeldung.jstl.dbaccess;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SQLConnection {
    private static String userName = "root";
    private static String password = "";
    private static final Logger LOGGER = LoggerFactory.getLogger(SQLConnection.class.getName());

    public static Connection getConnection() throws Exception {
        LOGGER.error("connecting...");
        Class.forName("com.mysql.cj.jdbc.Driver");
        LOGGER.error("class checked...");
        Connection conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", userName);
        connectionProps.put("password", password);
        conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false",
                connectionProps);
        LOGGER.info("Connected to database");
        return conn;
    }
}
