package com.baeldung.hibernate.persistmaps;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import com.baeldung.hibernate.Strategy;

public class HibernateUtil {
    private static String PROPERTY_FILE_NAME;
    private HibernateUtil() {
    }

    public static SessionFactory getSessionFactory() throws IOException {
        return getSessionFactory("");
    }

    public static SessionFactory getSessionFactory(String propertyFileName) throws IOException {
        if(propertyFileName.equals("")) propertyFileName = null;
        PROPERTY_FILE_NAME = propertyFileName;
        ServiceRegistry serviceRegistry = configureServiceRegistry();
        return makeSessionFactory(serviceRegistry);
    }

    public static SessionFactory getSessionFactory(Strategy strategy) {
        return buildSessionFactory(strategy);
    }

    private static SessionFactory buildSessionFactory(Strategy strategy) {
        try {
            ServiceRegistry serviceRegistry = configureServiceRegistry();

            MetadataSources metadataSources = new MetadataSources(serviceRegistry);

            for (Class<?> entityClass : strategy.getEntityClasses()) {
                metadataSources.addAnnotatedClass(entityClass);
            }

            Metadata metadata = metadataSources.getMetadataBuilder()
                .build();

            return metadata.getSessionFactoryBuilder()
                .build();
        } catch (IOException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static SessionFactory makeSessionFactory(ServiceRegistry serviceRegistry) {
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);

        metadataSources.addPackage("com.baeldung.hibernate.pojo");


        Metadata metadata = metadataSources.getMetadataBuilder()
            .build();

        return metadata.getSessionFactoryBuilder()
            .build();

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
            .getResource(StringUtils.defaultString(PROPERTY_FILE_NAME, "hibernate.properties"));
        try (FileInputStream inputStream = new FileInputStream(propertiesURL.getFile())) {
            properties.load(inputStream);
        }
        return properties;
    }
}
