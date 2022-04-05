package com.baeldung.hibernate.namingstrategy;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NamingStrategyLiveTest {

    private Session session;

    @Before
    public void init() {
        try {
            Configuration configuration = new Configuration();

            Properties properties = new Properties();
            properties.load(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("hibernate-namingstrategy.properties"));

            configuration.setProperties(properties);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
                .build();
            MetadataSources metadataSources = new MetadataSources(serviceRegistry);
            metadataSources.addAnnotatedClass(Customer.class);

            SessionFactory factory = metadataSources.buildMetadata()
                .buildSessionFactory();

            session = factory.openSession();
        } catch (HibernateException | IOException e) {
            fail("Failed to initiate Hibernate Session [Exception:" + e.toString() + "]");
        }
    }

    @After
    public void close() {
        if (session != null)
            session.close();
    }

    @Test
    public void testCustomPhysicalNamingStrategy() {

        Customer customer = new Customer();
        customer.setFirstName("first name");
        customer.setLastName("last name");
        customer.setEmailAddress("customer@example.com");

        session.beginTransaction();

        Long id = (Long) session.save(customer);

        session.flush();
        session.clear();

        Object[] result = (Object[]) session.createNativeQuery("select c.first_name, c.last_name, c.email from customers c where c.id = :id")
            .setParameter("id", id)
            .getSingleResult();

    }
}
