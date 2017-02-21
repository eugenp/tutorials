package com.baeldung.hibernate;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.ServiceRegistry;

public class HibernateMultiTenantUtil {
    private static SessionFactory sessionFactory;
    private static Map<String, ConnectionProvider> connectionProviderMap = new HashMap<>();
    private static final String[] tenantDBNames = { "db1", "db2"};

    public static SessionFactory getSessionFactory() throws UnsupportedTenancyException {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration().configure();
            ServiceRegistry serviceRegistry = configureServiceRegistry(configuration);
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }

    private static ServiceRegistry configureServiceRegistry(Configuration configuration) throws UnsupportedTenancyException {
        Properties properties = configuration.getProperties();

        connectionProviderMap = setUpConnectionProviders(properties, tenantDBNames);
        properties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, new ConfigurableMultiTenantConnectionProvider(connectionProviderMap));

        return new StandardServiceRegistryBuilder().applySettings(properties).build();
    }

    private static Map<String, ConnectionProvider> setUpConnectionProviders(Properties properties, String[] tenantNames) throws UnsupportedTenancyException {
        Map<String, ConnectionProvider> providerMap = new HashMap<>();
        for (String tenant : tenantNames) {
            DriverManagerConnectionProviderImpl connectionProvider = new DriverManagerConnectionProviderImpl();

            String tenantStrategy = properties.getProperty("hibernate.multiTenancy");
            System.out.println("Strategy:"+tenantStrategy);      
            properties.put(Environment.URL, tenantUrl(properties.getProperty(Environment.URL), tenant, tenantStrategy));
            System.out.println("URL:"+properties.getProperty(Environment.URL));
            connectionProvider.configure(properties);
            System.out.println("Tenant:"+tenant);
            providerMap.put(tenant, connectionProvider);
            
        }
        System.out.println("Added connections for:");
        providerMap.keySet().stream().forEach(System.out::println);
        return providerMap;
    }

    private static Object tenantUrl(String originalUrl, String tenant, String tenantStrategy) throws UnsupportedTenancyException {
        if (tenantStrategy.toUpperCase().equals("DATABASE")) {
            return originalUrl.replace(DEFAULT_DB_NAME, tenant);
        } else if (tenantStrategy.toUpperCase().equals("SCHEMA")) {
            return originalUrl + String.format(SCHEMA_TOKEN, tenant);
        } else {
            throw new UnsupportedTenancyException("Not yet supported");
        }
    }

    public static final String SCHEMA_TOKEN = ";INIT=CREATE SCHEMA IF NOT EXISTS %1$s\\;SET SCHEMA %1$s";
    public static final String DEFAULT_DB_NAME = "db1";

}
