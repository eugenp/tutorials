package com.baeldung.hibernate.multitenancy.schema;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.junit.Assert;

@SuppressWarnings("serial")
public class SchemaMultiTenantConnectionProvider extends AbstractMultiTenantConnectionProvider {

    private final ConnectionProvider connectionProvider = initConnectionProvider();

    @Override
    protected ConnectionProvider getAnyConnectionProvider() {
        return connectionProvider;
    }

    @Override
    protected ConnectionProvider selectConnectionProvider(String tenantIdentifier) {
        return connectionProvider;
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        Connection connection = super.getConnection(tenantIdentifier);
        connection.createStatement()
            .execute(String.format("SET SCHEMA %s;", tenantIdentifier));
        return connection;
    }

    private ConnectionProvider initConnectionProvider() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/hibernate-schema-multitenancy.properties"));
        } catch (IOException e) {
            Assert.fail("Error loading resource. Cause: " + e.getMessage());
        }

        DriverManagerConnectionProviderImpl connectionProvider = new DriverManagerConnectionProviderImpl();
        connectionProvider.configure(properties);
        return connectionProvider;
    }

}
