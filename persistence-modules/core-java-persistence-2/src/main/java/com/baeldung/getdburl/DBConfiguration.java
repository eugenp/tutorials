package com.baeldung.getdburl;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConfiguration {

    public static Connection getConnection() throws Exception {
        Class.forName("org.h2.Driver");
        String url = "jdbc:h2:mem:testdb";
        return DriverManager.getConnection(url, "user", "password");
    }
}
