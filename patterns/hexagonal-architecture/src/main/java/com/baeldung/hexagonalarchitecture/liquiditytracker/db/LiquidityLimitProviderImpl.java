package com.baeldung.hexagonalarchitecture.liquiditytracker.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.LiquidityLimitProvider;
import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.data.LiquidityLimitData;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class LiquidityLimitProviderImpl implements LiquidityLimitProvider{
    private static final Logger log = LoggerFactory.getLogger(LiquidityLimitProviderImpl.class);
    
    private static final String LIQUIDITY_LIMIT = "liquidity_limit";    
    private static final String ID = "id";
    private static final String AMOUNT = "amount";
    
    private static final String SELECT_SQL = "select " + ID + ", " + AMOUNT + " from " + 
            LIQUIDITY_LIMIT + " where " + ID + " = (select max(" + ID + ") from " + LIQUIDITY_LIMIT + ")";
    
    private Connection connection;
    private PreparedStatement statement;
    
    public void start() throws SQLException{
        statement = connection.prepareStatement(SELECT_SQL);    
    }
    
    @Override
    public LiquidityLimitData provide() {
        ResultSet rs;
        try {
            rs = statement.executeQuery();
        } catch (SQLException ex) {
            log.error("Unable to execute 'select' query, SQL statement: " + SELECT_SQL, ex);
            return null;
        }

        long id = -1;
        long amount = -1;        
        try {
            boolean result = rs.next();
            if (!result) {
                return new LiquidityLimitData(0, 0);
            }                
            id = rs.getLong(ID);
            amount = rs.getLong(AMOUNT);
        } catch (SQLException ex) {
            log.error(ex.toString(), ex);
            return null;
        }
        
        LiquidityLimitData data = new LiquidityLimitData(id, amount);
        return data;
    }
    
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
