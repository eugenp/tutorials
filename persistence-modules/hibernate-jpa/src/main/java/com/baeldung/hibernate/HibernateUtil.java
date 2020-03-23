package com.baeldung.hibernate;

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

import com.baeldung.hibernate.entities.DeptEmployee;
import com.baeldung.hibernate.optimisticlocking.OptimisticLockingCourse;
import com.baeldung.hibernate.optimisticlocking.OptimisticLockingStudent;
import com.baeldung.hibernate.pessimisticlocking.Individual;
import com.baeldung.hibernate.pessimisticlocking.PessimisticLockingCourse;
import com.baeldung.hibernate.pessimisticlocking.PessimisticLockingEmployee;
import com.baeldung.hibernate.pessimisticlocking.PessimisticLockingStudent;
import com.baeldung.hibernate.pojo.Person;
import com.baeldung.hibernate.pojo.Post;
import com.baeldung.hibernate.pojo.Student;

public class HibernateUtil {
    private static String PROPERTY_FILE_NAME;

    public static SessionFactory getSessionFactory() throws IOException {
        return getSessionFactory(null);
    }

    public static SessionFactory getSessionFactory(String propertyFileName) throws IOException {
        PROPERTY_FILE_NAME = propertyFileName;
        ServiceRegistry serviceRegistry = configureServiceRegistry();
        return makeSessionFactory(serviceRegistry);
    }

    public static SessionFactory getSessionFactoryByProperties(Properties properties) throws IOException {
        ServiceRegistry serviceRegistry = configureServiceRegistry(properties);
        return makeSessionFactory(serviceRegistry);
    }

    private static SessionFactory makeSessionFactory(ServiceRegistry serviceRegistry) {
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);

        metadataSources.addPackage("com.baeldung.hibernate.pojo");
        metadataSources.addAnnotatedClass(Person.class);
        metadataSources.addAnnotatedClass(Student.class);
        metadataSources.addAnnotatedClass(Individual.class);
        metadataSources.addAnnotatedClass(PessimisticLockingEmployee.class);
        metadataSources.addAnnotatedClass(PessimisticLockingStudent.class);
        metadataSources.addAnnotatedClass(PessimisticLockingCourse.class);
        metadataSources.addAnnotatedClass(com.baeldung.hibernate.pessimisticlocking.Customer.class);
        metadataSources.addAnnotatedClass(com.baeldung.hibernate.pessimisticlocking.Address.class);
        metadataSources.addAnnotatedClass(DeptEmployee.class);
        metadataSources.addAnnotatedClass(com.baeldung.hibernate.entities.Department.class);
        metadataSources.addAnnotatedClass(OptimisticLockingCourse.class);
        metadataSources.addAnnotatedClass(OptimisticLockingStudent.class);
        metadataSources.addAnnotatedClass(Post.class);

        Metadata metadata = metadataSources.getMetadataBuilder()
                .build();

        return metadata.getSessionFactoryBuilder()
                .build();

    }

    private static ServiceRegistry configureServiceRegistry() throws IOException {
        return configureServiceRegistry(getProperties());
    }

    private static ServiceRegistry configureServiceRegistry(Properties properties) throws IOException {
        return new StandardServiceRegistryBuilder().applySettings(properties)
                .build();
    }

    public static Properties getProperties() throws IOException {
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