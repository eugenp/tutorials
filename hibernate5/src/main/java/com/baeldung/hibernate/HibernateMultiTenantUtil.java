package com.baeldung.hibernate;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.ServiceRegistry;

import com.baeldung.hibernate.pojo.Supplier;

public class HibernateMultiTenantUtil {
    private static SessionFactory sessionFactory;
    private static Map<String, ConnectionProvider> connectionProviderMap = new HashMap<>();
    private static final String[] tenantDBNames = { "mydb1", "mydb2" };

    public static SessionFactory getSessionFactory() throws UnsupportedTenancyException, IOException {
        if (sessionFactory == null) {
            // Configuration configuration = new Configuration().configure();
            ServiceRegistry serviceRegistry = configureServiceRegistry();
            sessionFactory = makeSessionFactory(serviceRegistry);

        }
        return sessionFactory;
    }

    private static SessionFactory makeSessionFactory(ServiceRegistry serviceRegistry) {
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        for (Class annotatedClasses : getAnnotatedClasses()) {
            metadataSources.addAnnotatedClass(annotatedClasses);
        }

        Metadata metadata = metadataSources.buildMetadata();
        return metadata.getSessionFactoryBuilder()
            .build();

    }

    private static Class<?>[] getAnnotatedClasses() {
        return new Class<?>[] { Supplier.class };
    }

    private static ServiceRegistry configureServiceRegistry() throws UnsupportedTenancyException, IOException {

        // Properties properties = configuration.getProperties();
        Properties properties = getProperties();

        connectionProviderMap = setUpConnectionProviders(properties, tenantDBNames);
        properties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, new ConfigurableMultiTenantConnectionProvider(connectionProviderMap));

        return new StandardServiceRegistryBuilder().applySettings(properties)
            .build();
    }

    private static Properties getProperties() throws IOException {
        Properties properties = new Properties();
        URL propertiesURL = Thread.currentThread()
            .getContextClassLoader()
            .getResource("hibernate-multitenancy.properties");
        FileInputStream inputStream = new FileInputStream(propertiesURL.getFile());
        properties.load(inputStream);
        System.out.println("LOADED PROPERTIES FROM hibernate.properties");

        return properties;
    }

    private static Map<String, ConnectionProvider> setUpConnectionProviders(Properties properties, String[] tenantNames) throws UnsupportedTenancyException {
        Map<String, ConnectionProvider> providerMap = new HashMap<>();
        for (String tenant : tenantNames) {
            DriverManagerConnectionProviderImpl connectionProvider = new DriverManagerConnectionProviderImpl();

            String tenantStrategy = properties.getProperty("hibernate.multiTenancy");
            System.out.println("Strategy:" + tenantStrategy);
            properties.put(Environment.URL, tenantUrl(properties.getProperty(Environment.URL), tenant, tenantStrategy));
            System.out.println("URL:" + properties.getProperty(Environment.URL));
            connectionProvider.configure(properties);
            System.out.println("Tenant:" + tenant);
            providerMap.put(tenant, connectionProvider);

        }
        System.out.println("Added connections for:");
        providerMap.keySet()
            .stream()
            .forEach(System.out::println);
        return providerMap;
    }

    private static Object tenantUrl(String originalUrl, String tenant, String tenantStrategy) throws UnsupportedTenancyException {
        if (tenantStrategy.toUpperCase()
            .equals("DATABASE")) {
            return originalUrl.replace(DEFAULT_DB_NAME, tenant);
        } else if (tenantStrategy.toUpperCase()
            .equals("SCHEMA")) {
            return originalUrl + String.format(SCHEMA_TOKEN, tenant);
        } else {
            throw new UnsupportedTenancyException("Not yet supported");
        }
    }

    public static final String SCHEMA_TOKEN = ";INIT=CREATE SCHEMA IF NOT EXISTS %1$s\\;SET SCHEMA %1$s";
    public static final String DEFAULT_DB_NAME = "mydb1";

}
