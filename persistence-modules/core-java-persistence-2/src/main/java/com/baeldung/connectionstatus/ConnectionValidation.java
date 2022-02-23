package com.baeldung.connectionstatus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionValidation
{

    public static Connection getConnection()
            throws Exception
    {
        Class.forName("org.h2.Driver");
        String url = "jdbc:h2:mem:testdb";
        return DriverManager.getConnection(url, "user", "password");
    }

    public static void runIfOpened(Connection connection)
            throws SQLException
    {
        if (connection != null && !connection.isClosed()) {
            // run sql statements
        }
        else {
            // handle closed connection
        }
    }

    public static void runIfValid(Connection connection)
            throws SQLException
    {
        // Try to validate connection with a 5 seconds timeout
        if (connection.isValid(5)) {
            // run sql statements
        }
        else {
            // handle invalid connection
        }
    }

    public static void runIfConnectionValid(Connection connection)
    {
        if (isConnectionValid(connection)) {
            // run sql statements
        }
        else {
            // handle invalid connection
        }
    }

    public static boolean isConnectionValid(Connection connection)
    {
        try {
            if (connection != null && !connection.isClosed()) {
                // Running a simple validation query
                connection.prepareStatement("SELECT 1");
                return true;
            }
        }
        catch (SQLException e) {
            // log some useful data here
        }
        return false;
    }
}
