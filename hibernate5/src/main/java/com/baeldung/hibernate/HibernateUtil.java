package com.baeldung.hibernate;

import com.baeldung.hibernate.pessimisticlocking.Individual;
import com.baeldung.hibernate.pessimisticlocking.PessimisticLockingCourse;
import com.baeldung.hibernate.pessimisticlocking.PessimisticLockingEmployee;
import com.baeldung.hibernate.pessimisticlocking.PessimisticLockingStudent;
import com.baeldung.hibernate.pojo.*;
import com.baeldung.hibernate.pojo.Person;
import com.baeldung.hibernate.pojo.inheritance.*;
import org.apache.commons.lang3.StringUtils;
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
    private static String PROPERTY_FILE_NAME;

    public static SessionFactory getSessionFactory() throws IOException {
        return getSessionFactory(null);
    }

    public static SessionFactory getSessionFactory(String propertyFileName) throws IOException {
        PROPERTY_FILE_NAME = propertyFileName;
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
        metadataSources.addAnnotatedClass(Book.class);
        metadataSources.addAnnotatedClass(MyEmployee.class);
        metadataSources.addAnnotatedClass(MyProduct.class);
        metadataSources.addAnnotatedClass(Pen.class);
        metadataSources.addAnnotatedClass(Person.class);
        metadataSources.addAnnotatedClass(Animal.class);
        metadataSources.addAnnotatedClass(Pet.class);
        metadataSources.addAnnotatedClass(Vehicle.class);
        metadataSources.addAnnotatedClass(Car.class);
        metadataSources.addAnnotatedClass(Bag.class);
        metadataSources.addAnnotatedClass(PointEntity.class);
        metadataSources.addAnnotatedClass(PolygonEntity.class);
        metadataSources.addAnnotatedClass(com.baeldung.hibernate.pojo.Person.class);
        metadataSources.addAnnotatedClass(Individual.class);
        metadataSources.addAnnotatedClass(PessimisticLockingEmployee.class);
        metadataSources.addAnnotatedClass(PessimisticLockingStudent.class);
        metadataSources.addAnnotatedClass(PessimisticLockingCourse.class);
        metadataSources.addAnnotatedClass(com.baeldung.hibernate.pessimisticlocking.Customer.class);
        metadataSources.addAnnotatedClass(com.baeldung.hibernate.pessimisticlocking.Address.class);

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
          .getResource(StringUtils.defaultString(PROPERTY_FILE_NAME, "hibernate.properties"));
        try (FileInputStream inputStream = new FileInputStream(propertiesURL.getFile())) {
            properties.load(inputStream);
        }
        return properties;
    }
}