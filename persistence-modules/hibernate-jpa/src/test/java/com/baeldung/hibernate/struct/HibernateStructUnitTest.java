package com.baeldung.hibernate.struct;

import com.baeldung.hibernate.struct.entities.StructDepartment;
import com.baeldung.hibernate.struct.entities.StructManager;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class HibernateStructUnitTest {

    private Session session;
    private Transaction transaction;

    public static SessionFactory getSessionFactoryByProperties(Properties properties) throws IOException {
        ServiceRegistry serviceRegistry = configureServiceRegistry(properties);
        return makeSessionFactory(serviceRegistry);
    }

    private static SessionFactory makeSessionFactory(ServiceRegistry serviceRegistry) {
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        metadataSources.addPackage("com.baeldung.hibernate.struct.entities");
        metadataSources.addAnnotatedClass(StructDepartment.class);
        metadataSources.addAnnotatedClass(StructManager.class);

        Metadata metadata = metadataSources.getMetadataBuilder()
          .build();

        return metadata.getSessionFactoryBuilder()
          .build();

    }
    private static ServiceRegistry configureServiceRegistry(Properties properties) throws IOException {
        return new StandardServiceRegistryBuilder().applySettings(properties)
          .build();
    }

    public static Properties getProperties() throws IOException {
        Properties properties = new Properties();
        URL propertiesURL = Thread.currentThread()
          .getContextClassLoader()
          .getResource(StringUtils.defaultString(PROPERTY_FILE_NAME, "hibernate-derby.properties"));
        try (FileInputStream inputStream = new FileInputStream(propertiesURL.getFile())) {
            properties.load(inputStream);
        }
        return properties;
    }

    private static ServiceRegistry configureServiceRegistry() throws IOException {
        return configureServiceRegistry(getProperties());
    }
    private static String PROPERTY_FILE_NAME;
    public static SessionFactory getSessionFactory(String propertyFileName) throws IOException {
        PROPERTY_FILE_NAME = propertyFileName;
        ServiceRegistry serviceRegistry = configureServiceRegistry();
        return makeSessionFactory(serviceRegistry);
    }
    @Before
    public void setUp() throws IOException {
        session = getSessionFactory("hibernate-derby.properties")
                .openSession();
        transaction = session.beginTransaction();

        transaction.commit();
        transaction = session.beginTransaction();
    }

    @After
    public void tearDown() {
        transaction.rollback();
        session.close();
    }



/*  This unit test is for reference only because the Hibernate dialect for
    Apache Derby database does not support @Struct annotation.

    @Test
    public void givenSaveDepartmentObject_ThenManagerUDTExists() {
        StructDepartment d = new StructDepartment("Information Technology"
                , new StructManager("Zeeshan", "Arif", "Qualified"));

        session.persist(d);
        session.flush();
        session.clear();

    }*/
}
