package com.baeldung.persistjson;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class StoreUnitTest {

    private Session session;

    @BeforeEach
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
            metadataSources.addAnnotatedClass(Store.class);


            SessionFactory factory = metadataSources.buildMetadata()
                .buildSessionFactory();

            session = factory.openSession();
        } catch (HibernateException | IOException e) {
            fail("Failed to initiate Hibernate Session [Exception:" + e + "]");
        }
    }

    @AfterEach
    public void close() {
        if (session != null)
            session.close();
    }

    @Test
    public void givenStoreWithJsonAttributes_whenSavingAndRetrievingUsingJdbcTypeCodeAnnotation_thenJsonAttributesArePersistedAndRetrievedCorrectly() {
        Store store = new Store();
        store.setName("Walmart");
        store.setLocation("123 Main Street");

        Map<String, String> attributes = new HashMap<>();
        attributes.put("country", "USA");
        attributes.put("industry", "e-commerce");
        attributes.put("capacity", "1000");
        store.setAttributes(attributes);

        session.beginTransaction();
        session.save(store);
        session.flush();
        session.clear();

        Store result = session.createNativeQuery("select * from store where store.id = :id", Store.class)
            .setParameter("id", 1)
            .getSingleResult();

        assertEquals(3, result.getAttributes()
            .size());
        assertEquals("USA", result.getAttributes()
            .get("country"));
        assertEquals("e-commerce", result.getAttributes()
            .get("industry"));
        assertEquals("1000", result.getAttributes()
            .get("capacity"));
    }

}