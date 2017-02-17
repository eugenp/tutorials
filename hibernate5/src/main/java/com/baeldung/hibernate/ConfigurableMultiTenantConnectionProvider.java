package com.baeldung.hibernate;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

public class ConfigurableMultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider {

    private final Map<String, ConnectionProvider> connectionProviderMap = 
            new HashMap<>();

    
    public ConfigurableMultiTenantConnectionProvider(
            Map<String, ConnectionProvider> connectionProviderMap) {
        this.connectionProviderMap.putAll( connectionProviderMap );
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
    
}