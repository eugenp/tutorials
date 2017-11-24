package com.stackify.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionUtil {

    private static Logger logger = LogManager.getLogger(ConnectionUtil.class);

    public static Connection getConnection() {
        try {
            String jndiName = "java:/comp/env/jdbc/MyDataSource";
            
            Context initialContext = new InitialContext();
            DataSource datasource = (DataSource)initialContext.lookup(jndiName);
            if (datasource != null) {
              return datasource.getConnection();
            }
            else {
              logger.error("Failed to lookup datasource.");
            }
        }

        catch (NamingException | SQLException exc) {
            logger.error(exc.getMessage());
        }
        return null;
    }

}
