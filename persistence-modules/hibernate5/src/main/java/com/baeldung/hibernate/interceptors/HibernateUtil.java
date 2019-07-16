package com.baeldung.hibernate.interceptors;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.SessionFactoryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import com.baeldung.hibernate.interceptors.entity.User;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static String PROPERTY_FILE_NAME;

    public static SessionFactory getSessionFactory() throws IOException {
        return getSessionFactory(null);
    }

    public static SessionFactory getSessionFactory(String propertyFileName) throws IOException {
        PROPERTY_FILE_NAME = propertyFileName;
        if (sessionFactory == null) {
            ServiceRegistry serviceRegistry = configureServiceRegistry();
            sessionFactory = getSessionFactoryBuilder(serviceRegistry).build();
        }
        return sessionFactory;
    }

    public static SessionFactory getSessionFactoryWithInterceptor(String propertyFileName, Interceptor interceptor) throws IOException {
        PROPERTY_FILE_NAME = propertyFileName;
        if (sessionFactory == null) {
            ServiceRegistry serviceRegistry = configureServiceRegistry();
            sessionFactory = getSessionFactoryBuilder(serviceRegistry).applyInterceptor(interceptor)
                .build();
        }
        return sessionFactory;
    }

    public static Session getSessionWithInterceptor(Interceptor interceptor) throws IOException {
        return getSessionFactory().withOptions()
            .interceptor(interceptor)
            .openSession();
    }

    private static SessionFactoryBuilder getSessionFactoryBuilder(ServiceRegistry serviceRegistry) {
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addPackage("com.baeldung.hibernate.interceptors");
        metadataSources.addAnnotatedClass(User.class);

        Metadata metadata = metadataSources.buildMetadata();
        return metadata.getSessionFactoryBuilder();

    }

    private static ServiceRegistry configureServiceRegistry() throws IOException {
        Properties properties = getProperties();
        return new StandardServiceRegistryBuilder().applySettings(properties)
            .build();
    }

    private static Properties getProperties() throws IOException {
        Properties properties = new Properties();
        URL propertiesURL = Thread.currentThread()
            .getContextClassLoader()
            .getResource(StringUtils.defaultString(PROPERTY_FILE_NAME, "hibernate-interceptors.properties"));
        try (FileInputStream inputStream = new FileInputStream(propertiesURL.getFile())) {
            properties.load(inputStream);
        }
        return properties;
    }
}