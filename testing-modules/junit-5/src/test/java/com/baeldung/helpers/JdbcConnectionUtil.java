package com.baeldung.helpers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConnectionUtil {

    private static Connection con;

    public static Connection getConnection() {
        if (con == null || isClosed(con)) {
            try {
                Properties props = new Properties();
                props.load(JdbcConnectionUtil.class.getResourceAsStream("jdbc.properties"));

                String jdbcUrl = props.getProperty("jdbc.url");
                String driver = props.getProperty("jdbc.driver");
                String username = props.getProperty("jdbc.user");
                String password = props.getProperty("jdbc.password");
                con = getConnection(jdbcUrl, driver, username, password);
                return con;
            } catch (IOException exc) {
                exc.printStackTrace();
            }

            return null;
        }
        return con;
    }

    public static Connection getConnection(String jdbcUrl, String driver, String username, String password) {
        if (con == null || isClosed(con)) {
            try {
                Class.forName(driver);
                con = DriverManager.getConnection(jdbcUrl, username, password);
                return con;
            } catch (ClassNotFoundException exc) {
                exc.printStackTrace();
            } catch (SQLException exc) {
                exc.printStackTrace();
            }

            return null;
        }

        return con;
    }

    private static boolean isClosed(Connection con) {
        try {
            return con.isClosed();
        } catch (SQLException e) {
            return true;
        }
    }
}
