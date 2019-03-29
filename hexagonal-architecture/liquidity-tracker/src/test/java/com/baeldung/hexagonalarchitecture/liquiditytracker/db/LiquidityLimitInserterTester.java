package com.baeldung.hexagonalarchitecture.liquiditytracker.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class LiquidityLimitInserterTester {
    private static final Logger log = LoggerFactory.getLogger(LiquidityLimitInserterTester.class);
    private Connection connection;
    
    public static void main(String[] args) {
        new LiquidityLimitInserterTester().run(args[0], args[1], args[2], args[3]);
    }
    
    private void run(String hostname, String dbName, String username, String password){
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://fedoravm:5432/liquidity_db", "app_user1",
                    "22pass01");
        } catch (SQLException ex) {
          String errorMessage = "Unable to connect to database.";
          log.error(errorMessage, ex); 
          return;
        }
        
        LiquidityLimitInserter inserter = new LiquidityLimitInserter();
        inserter.setConnection(connection);
        try {
            inserter.start();
        } catch (SQLException ex) {
            closeConnection();
            return;
        }
        
        inserter.insert(1, 1000);
        closeConnection();
    }
    
    private void closeConnection(){
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
