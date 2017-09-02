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

public abstract  class HibernateMultiTenantUtil {
    private static SessionFactory sessionFactory;
    private  Map<String, ConnectionProvider> connectionProviderMap = new HashMap<>();
    private static final String[] tenantDBNames = { "mydb1", "mydb2" };
    

    public  SessionFactory getSessionFactory() throws UnsupportedTenancyException, IOException {
        if (sessionFactory == null) {
            ServiceRegistry serviceRegistry = configureServiceRegistry();
            sessionFactory = makeSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }

    private SessionFactory makeSessionFactory(ServiceRegistry serviceRegistry) {
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        for (Class annotatedClasses : getAnnotatedClasses()) {
            metadataSources.addAnnotatedClass(annotatedClasses);
        }

        Metadata metadata = metadataSources.buildMetadata();
        return metadata.getSessionFactoryBuilder()
            .build();

    }

    private Class<?>[] getAnnotatedClasses() {
        return new Class<?>[] { Supplier.class };
    }

    private ServiceRegistry configureServiceRegistry() throws UnsupportedTenancyException, IOException {

        Properties properties = getProperties();
        String tenantStrategy = properties.getProperty("hibernate.multiTenancy");
        connectionProviderMap = setUpConnectionProviders(properties, tenantDBNames);
        properties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, new ConfigurableMultiTenantConnectionProvider(connectionProviderMap, tenantStrategy));

        return new StandardServiceRegistryBuilder().applySettings(properties)
            .build();
    }

    private Properties getProperties() throws IOException {
        Properties properties = new Properties();
        URL propertiesURL = Thread.currentThread()
            .getContextClassLoader()
            .getResource("hibernate.properties");
        FileInputStream inputStream = new FileInputStream(propertiesURL.getFile());
        properties.load(inputStream);
        System.out.println("LOADED PROPERTIES FROM hibernate.properties");

        return properties;
    }

    private Map<String, ConnectionProvider> setUpConnectionProviders(Properties properties, String[] tenantNames) throws UnsupportedTenancyException {
        Map<String, ConnectionProvider> providerMap = new HashMap<>();
        for (String tenant : tenantNames) {
            DriverManagerConnectionProviderImpl connectionProvider = new DriverManagerConnectionProviderImpl();

            String tenantStrategy = properties.getProperty("hibernate.multiTenancy");
            System.out.println("Strategy:" + tenantStrategy);
            properties.put(Environment.URL, tenantUrl(properties.getProperty(Environment.URL), tenant));
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


    
    public abstract Object tenantUrl(String originalUrl, String tenant) throws UnsupportedTenancyException;



}
