package com.baeldung.hexagonalarchitecture.liquiditytracker.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class PostgreSqlExample {
    private static final Logger log = LoggerFactory.getLogger(PostgreSqlExample.class);
    
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;
    
    public static void main(String[] args){
        log.info("Arguments: " + Arrays.toString(args));
        
        new PostgreSqlExample().run(args[0], args[1], args[2], args[3]);
    }
    
    public void run(String hostname, String dbName, String username, String password) {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://" + hostname + ":5432/"
                    + dbName, username, password);
        } catch (SQLException ex) {
          String errorMessage = "Unable to connect to database.";
          log.error(errorMessage, ex); 
          return;
        }
        
        try {
            statement = connection.prepareStatement("select count(*) from liquidity_limit");
        } catch (SQLException ex) {
            log.error("Unable to create the prepared statement");
            closeEverything();
            return;
        }
        
        try {
            resultSet = statement.executeQuery();
        } catch (SQLException ex) {
            log.error("Unable to execute SQL query.");
            closeEverything();
            return;
        }  

        long numberOfRecords = -1;
        try {
            while (resultSet.next()) {
                numberOfRecords = resultSet.getLong(1);
            }
        } catch (SQLException ex) {
            log.error("Unable to get data from the JDBC result set", ex);
            closeEverything();
            return;
        }
        closeEverything();
        
        log.info("Number of records: " + numberOfRecords);
    }

    private void closeEverything(){
        if (resultSet != null) {
            log.info("Closing the result set");
            try {
                resultSet.close();
            } catch (SQLException ex) {
                log.error(ex.toString(), ex);
            }
        }
        
        if (statement != null) {
            log.info("Closing the JDBC statement");
            try {
                statement.close();
            } catch (SQLException ex) {
                log.error(ex.toString(), ex);
            }
        }
        
        if (connection != null) {
            log.info("Closing the JDBC connection");
            try {
                connection.close();
            } catch (SQLException ex) {
                log.error(ex.toString(), ex);
            }
        }
    }
}
