package com.baeldung.helpers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcConnectionUtil {

    private static Connection con;

    public static Connection getConnection() {
        if (con == null) {
            try {
                Properties props = new Properties();
                props.load(JdbcConnectionUtil.class.getResourceAsStream("jdbc.properties"));
                Class.forName(props.getProperty("jdbc.driver"));
                con = DriverManager.getConnection(props.getProperty("jdbc.url"), props.getProperty("jdbc.user"), props.getProperty("jdbc.password"));
                return con;
            } catch (IOException exc) {
                exc.printStackTrace();
            } catch (ClassNotFoundException exc) {
                exc.printStackTrace();
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
            return null;
        }
        return con;
    }
}
