package com.baeldung.hibernate.persistjson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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

public class PersistJSONUnitTest {

    private Session session;

    @Before
    public void init() {
        try {
            Configuration configuration = new Configuration();

            Properties properties = new Properties();
            properties.load(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("hibernate-persistjson.properties"));

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
    public void givenCustomer_whenCallingSerializeCustomerAttributes_thenAttributesAreConverted() throws IOException {

        Customer customer = new Customer();
        customer.setFirstName("first name");
        customer.setLastName("last name");

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("address", "123 Main Street");
        attributes.put("zipcode", 12345);

        customer.setCustomerAttributes(attributes);

        customer.serializeCustomerAttributes();

        String serialized = customer.getCustomerAttributeJSON();

        customer.setCustomerAttributeJSON(serialized);
        customer.deserializeCustomerAttributes();

        Map<String, Object> deserialized = customer.getCustomerAttributes();

        assertEquals("123 Main Street", deserialized.get("address"));
    }

    @Test
    public void givenCustomer_whenSaving_thenAttributesAreConverted() {

        Customer customer = new Customer();
        customer.setFirstName("first name");
        customer.setLastName("last name");

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("address", "123 Main Street");
        attributes.put("zipcode", 12345);

        customer.setCustomerAttributes(attributes);

        session.beginTransaction();

        int id = (int) session.save(customer);

        session.flush();
        session.clear();

        Customer result = session.createNativeQuery("select * from Customers where Customers.id = :id", Customer.class)
            .setParameter("id", id)
            .getSingleResult();

        assertEquals(2, result.getCustomerAttributes()
            .size());
    }

}
