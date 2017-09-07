package com.baeldung.hibernate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

public class ConfigurableMultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider {

    private final Map<String, ConnectionProvider> connectionProviderMap = 
            new HashMap<>();
    private String strategy;
    
    public ConfigurableMultiTenantConnectionProvider(
            Map<String, ConnectionProvider> connectionProviderMap, String strategy) {
        this.connectionProviderMap.putAll( connectionProviderMap );
        this.strategy = strategy;
    }
    @Override
    protected ConnectionProvider getAnyConnectionProvider() {
        System.out.println("Any");
        return connectionProviderMap.values().iterator().next();
    }

    @Override
    protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
        System.out.println("Specific");
        return connectionProviderMap.get( tenantIdentifier );
    }
    
    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        Connection connection = super.getConnection(tenantIdentifier);
//        if (strategy.equals( "SCHEMA")){
//            connection.createStatement().execute("CREATE SCHEMA IF NOT EXISTS '" + tenantIdentifier + "'");
//            connection.createStatement().execute("SET SCHEMA '" + tenantIdentifier + "'");
//            
//        }
        
        return connection;
    }
    
}