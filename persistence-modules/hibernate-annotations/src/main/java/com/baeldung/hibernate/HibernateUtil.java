package com.baeldung.hibernate;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import static java.util.Objects.requireNonNull;

public class HibernateUtil {

    public static SessionFactory getSessionFactory(String propertyFileName, List<Class<?>> classes) throws IOException {
        ServiceRegistry serviceRegistry = configureServiceRegistry(propertyFileName);
        return makeSessionFactory(serviceRegistry, classes);
    }

    private static SessionFactory makeSessionFactory(ServiceRegistry serviceRegistry, List<Class<?>> classes) {
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        for (Class<?> clazz: classes) {
            metadataSources = metadataSources.addAnnotatedClass(clazz);
        }

        Metadata metadata = metadataSources
          .getMetadataBuilder()
          .build();

        return metadata.getSessionFactoryBuilder().build();
    }

    private static ServiceRegistry configureServiceRegistry(String propertyFileName) throws IOException {
        return configureServiceRegistry(getProperties(propertyFileName));
    }

    private static ServiceRegistry configureServiceRegistry(Properties properties) {
        return new StandardServiceRegistryBuilder().applySettings(properties)
                .build();
    }

    public static Properties getProperties(String propertyFileName) throws IOException {
        Properties properties = new Properties();

        String file = getResourceURL(propertyFileName).getFile();

        try (FileInputStream inputStream = new FileInputStream(file)) {
            properties.load(inputStream);
        }

        return properties;
    }

    private static URL getResourceURL(String propertyFileName) {
        return requireNonNull(Thread.currentThread()
          .getContextClassLoader()
          .getResource(StringUtils.defaultString(propertyFileName, "hibernate.properties")));
    }
}