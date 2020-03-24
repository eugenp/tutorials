package com.stackify.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionUtil {

    private static Logger logger = LogManager.getLogger(ConnectionUtil.class);

    public static Connection getConnection() {
        try {
            Properties props = new Properties();
            props.load(ConnectionUtil.class.getResourceAsStream("jdbc.properties"));
            Class.forName(props.getProperty("jdbc.driver"));
            Connection con = DriverManager.getConnection(props.getProperty("jdbc.url"), props.getProperty("jdbc.user"), props.getProperty("jdbc.password"));
            return con;
        }

        catch (FileNotFoundException exc) {
            logger.error(exc.getMessage());
        } catch (IOException exc) {
            logger.error(exc.getMessage());
        } catch (ClassNotFoundException exc) {
            logger.error(exc.getMessage());
        } catch (SQLException exc) {
            logger.error(exc.getMessage());
        }
        return null;
    }

}
