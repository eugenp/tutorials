package com.baeldung.hexagonalarchitecture.liquiditytracker.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.UtilizedLiquidityProvider;
import com.baeldung.hexagonalarchitecture.liquiditytracker.adapter.data.UtilizedLiquidityData;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class UtilizedLiquidityProviderImpl implements UtilizedLiquidityProvider {
    private static final Logger log = LoggerFactory.getLogger(UtilizedLiquidityProviderImpl.class);

    private static final String LIQUIDITY_UTILIZED = "liquidity_utilized";
    private static final String ID = "id";
    private static final String TOTAL_AMOUNT = "totalAmount";

    private static final String SELECT_SQL = "select " + ID + ", " + TOTAL_AMOUNT + " from " + LIQUIDITY_UTILIZED
            + " where " + ID + " = (select max(" + ID + ") from " + LIQUIDITY_UTILIZED + ")";

    private Connection connection;
    private PreparedStatement statement;

    public void start() throws SQLException {
        statement = connection.prepareStatement(SELECT_SQL);
    }

    @Override
    public UtilizedLiquidityData provide() {
        ResultSet rs;
        try {
            rs = statement.executeQuery();
        } catch (SQLException ex) {
            log.error("Unable to execute 'select' query, SQL statement: " + SELECT_SQL, ex);
            return null;
        }

        long id = -1;
        long totalAmount = -1;
        try {
            boolean result = rs.next();
            if (!result) {
                return new UtilizedLiquidityData(0, 0);
            }
            id = rs.getLong(ID);
            totalAmount = rs.getLong(TOTAL_AMOUNT);
        } catch (SQLException ex) {
            log.error(ex.toString(), ex);
            return null;
        }

        UtilizedLiquidityData data = new UtilizedLiquidityData(id, totalAmount);
        return data;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
