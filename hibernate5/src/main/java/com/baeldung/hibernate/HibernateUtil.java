package com.baeldung.hibernate;

import com.baeldung.hibernate.pojo.Employee;
import com.baeldung.hibernate.pojo.EntityDescription;
import com.baeldung.hibernate.pojo.OrderEntry;
import com.baeldung.hibernate.pojo.OrderEntryIdClass;
import com.baeldung.hibernate.pojo.OrderEntryPK;
import com.baeldung.hibernate.pojo.Product;
import com.baeldung.hibernate.pojo.Phone;
import com.baeldung.hibernate.pojo.TemporalValues;
import com.baeldung.hibernate.pojo.Course;
import com.baeldung.hibernate.pojo.Student;
import com.baeldung.hibernate.pojo.User;
import com.baeldung.hibernate.pojo.UserProfile;

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

    public static SessionFactory getSessionFactory() throws IOException {
        if (sessionFactory == null) {
            ServiceRegistry serviceRegistry = configureServiceRegistry();
            sessionFactory = makeSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }

    private static SessionFactory makeSessionFactory(ServiceRegistry serviceRegistry) {
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addPackage("com.baeldung.hibernate.pojo");
        metadataSources.addAnnotatedClass(Employee.class);
        metadataSources.addAnnotatedClass(Phone.class);
        metadataSources.addAnnotatedClass(EntityDescription.class);
        metadataSources.addAnnotatedClass(TemporalValues.class);
        metadataSources.addAnnotatedClass(User.class);
        metadataSources.addAnnotatedClass(Student.class);
        metadataSources.addAnnotatedClass(Course.class);
        metadataSources.addAnnotatedClass(Product.class);
        metadataSources.addAnnotatedClass(OrderEntryPK.class);
        metadataSources.addAnnotatedClass(OrderEntry.class);
        metadataSources.addAnnotatedClass(OrderEntryIdClass.class);
        metadataSources.addAnnotatedClass(UserProfile.class);

        Metadata metadata = metadataSources.buildMetadata();
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
                .getResource("hibernate.properties");
        try (FileInputStream inputStream = new FileInputStream(propertiesURL.getFile())) {
            properties.load(inputStream);
        }
        return properties;
    }

}