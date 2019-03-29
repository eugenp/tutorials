package com.baeldung.hexagonalarchitecture.liquiditytracker.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class LiquidityLimitInserter {
    private static final Logger log = LoggerFactory.getLogger(LiquidityLimitInserter.class);
    
    private static final String INSERT_SQL = "insert into liquidity_limit values(?,?,?)";
    
    private Connection connection;
    private PreparedStatement statement;
    
    public void start() throws SQLException{
        statement = connection.prepareStatement(INSERT_SQL);    
    }
    
    public void insert(long id, long amount){
        try {
            statement.setLong(1, id);
            Timestamp now = new Timestamp(new Date().getTime());
            statement.setTimestamp(2, now);
            statement.setLong(3, amount);
        } catch (SQLException ex) {
            log.error("Unable to set required arguments into the SQL prepared statement.", ex);
            return;
        }
        
        int recordCount = -1;
        try {
           recordCount = statement.executeUpdate();
        } catch (SQLException ex) {
            log.error("Unable to execute insertion", ex);
            return;
        }
        log.debug("Number of records inserted: " + recordCount);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}

