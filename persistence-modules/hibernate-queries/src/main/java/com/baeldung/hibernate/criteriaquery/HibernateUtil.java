package com.baeldung.hibernate.criteriaquery;

import com.baeldung.hibernate.customtypes.LocalDateStringType;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    private HibernateUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
        }
        return sessionFactory;
    }

    private static SessionFactory buildSessionFactory() {
        try {
            ServiceRegistry serviceRegistry = configureServiceRegistry();

            MetadataSources metadataSources = new MetadataSources(serviceRegistry);

            metadataSources.addAnnotatedClass(Student.class);
            Metadata metadata = metadataSources.getMetadataBuilder()
                    .applyBasicType(LocalDateStringType.INSTANCE)
                    .build();
            return metadata.getSessionFactoryBuilder().build();
        } catch (IOException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }


    private static ServiceRegistry configureServiceRegistry() throws IOException {
        Properties properties = getProperties();
        return new StandardServiceRegistryBuilder().applySettings(properties).build();
    }

    private static Properties getProperties() throws IOException {
        Properties properties = new Properties();
        URL propertiesURL = Thread.currentThread()
                .getContextClassLoader()
                .getResource("hibernate.properties");
        try (FileInputStream inputStream = new FileInputStream(propertiesURL.getFile())) {
            properties.load(inputStream);
        }
        return properties;
    }
}
