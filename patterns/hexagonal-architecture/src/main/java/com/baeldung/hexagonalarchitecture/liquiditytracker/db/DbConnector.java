package com.baeldung.hexagonalarchitecture.liquiditytracker.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.hexagonalarchitecture.liquiditytracker.config.DatabaseConfigValues;
import com.baeldung.hexagonalarchitecture.liquiditytracker.lifecycle.Stoppable;

/**
 * @author Víctor Gil
 *
 * since March 2019
 */
public class DbConnector implements Stoppable {
    private static final Logger log = LoggerFactory.getLogger(DbConnector.class);
    private Connection connection;

    private DatabaseConfigValues configValues;

    private LiquidityLimitProviderImpl liquidityLimitProvider;
    private LiquidityLimitSetterImpl liquidityLimitSetter;
    private UtilizedLiquidityProviderImpl utilizedLiquidityProvider;
    private UtilizedLiquiditySetterImpl utilizedLiquiditySetter;

    public void start() throws SQLException {
        connect(configValues.getDriverName(), configValues.getHostname(), configValues.getDatabaseName(),
                configValues.getUsername(), configValues.getPassword());

        liquidityLimitProvider.setConnection(connection);
        liquidityLimitSetter.setConnection(connection);
        utilizedLiquidityProvider.setConnection(connection);
        utilizedLiquiditySetter.setConnection(connection);
    }

    private void connect(String driverName, String hostname, String dbName, String username, String password)
            throws SQLException {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://" + hostname + ":5432/" + dbName, username,
                    password);
        } catch (SQLException ex) {
            String errorMessage = "Unable to connect to database.";
            log.error(errorMessage, ex);
            throw ex;
        }
    }

    @Override
    public void stop() {
        closeConnection();
    }

    private void closeConnection() {
        if (connection != null) {
            log.info("Closing the JDBC connection");
            try {
                connection.close();
            } catch (SQLException ex) {
                log.error("Unable to close the JDBC connection", ex);
            }
        }
    }

    // setters
    public void setLiquidityLimitProvider(LiquidityLimitProviderImpl liquidityLimitProvider) {
        this.liquidityLimitProvider = liquidityLimitProvider;
    }

    public void setLiquidityLimitSetter(LiquidityLimitSetterImpl liquidityLimitSetter) {
        this.liquidityLimitSetter = liquidityLimitSetter;
    }

    public void setUtilizedLiquidityProvider(UtilizedLiquidityProviderImpl utilizedLiquidityProvider) {
        this.utilizedLiquidityProvider = utilizedLiquidityProvider;
    }

    public void setUtilizedLiquiditySetter(UtilizedLiquiditySetterImpl utilizedLiquiditySetter) {
        this.utilizedLiquiditySetter = utilizedLiquiditySetter;
    }

    public void setConfigValues(DatabaseConfigValues configValues) {
        this.configValues = configValues;
    }
}
