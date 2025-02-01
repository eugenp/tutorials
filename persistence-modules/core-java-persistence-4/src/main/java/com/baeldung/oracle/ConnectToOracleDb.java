package com.baeldung.oracle;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

public class ConnectToOracleDb {

    public static Connection getConnection(String databaseUrl, String userName, String password) throws SQLException {

        var connectionProperties = new Properties();
        connectionProperties.setProperty(OracleConnection.CONNECTION_PROPERTY_USER_NAME, userName);
        connectionProperties.setProperty(OracleConnection.CONNECTION_PROPERTY_PASSWORD, password);

        var oracleDataSource = new OracleDataSource();
        oracleDataSource.setConnectionProperties(connectionProperties);
        oracleDataSource.setURL(databaseUrl);

        return oracleDataSource.getConnection();
    }
}