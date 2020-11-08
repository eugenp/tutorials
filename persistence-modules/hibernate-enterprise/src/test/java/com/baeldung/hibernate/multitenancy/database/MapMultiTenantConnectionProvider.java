package com.baeldung.hibernate.multitenancy.database;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;

@SuppressWarnings("serial")
public class MapMultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider {

    private final Map<String, ConnectionProvider> connectionProviderMap = new HashMap<>();

    public MapMultiTenantConnectionProvider() throws IOException {
        initConnectionProviderForTenant(TenantIdNames.MYDB1);
        initConnectionProviderForTenant(TenantIdNames.MYDB2);
    }

    @Override
    protected ConnectionProvider getAnyConnectionProvider() {
        return connectionProviderMap.values()
            .iterator()
            .next();
    }

    @Override
    protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
        return connectionProviderMap.get(tenantIdentifier);
    }

    private void initConnectionProviderForTenant(String tenantId) throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getResourceAsStream(String.format("/hibernate-database-%s.properties", tenantId)));
        DriverManagerConnectionProviderImpl connectionProvider = new DriverManagerConnectionProviderImpl();
        connectionProvider.configure(properties);
        this.connectionProviderMap.put(tenantId, connectionProvider);
    }

}
