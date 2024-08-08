package com.baeldung.persistjson;

import jakarta.persistence.TypedQuery;
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
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class PhoneLiveTest {

    private Session session;

    @BeforeEach
    public void init() {
        try {
            Configuration configuration = new Configuration();

            Properties properties = new Properties();
            properties.load(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("hibernate-persistjsonpostgresql.properties"));

            configuration.setProperties(properties);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
                .build();
            MetadataSources metadataSources = new MetadataSources(serviceRegistry);
            metadataSources.addAnnotatedClass(Phone.class);

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
    public void givenPhoneWithJsonAttributesAsEmbeddableObject_whenSavingAndRetrievingPhoneWithSpecificationObjectAsEmbeddable_thenJsonAttributesArePersistedAndRetrievedCorrectly() {
        Phone phone = new Phone();
        phone.setName("IPhone");

        Specification specification = new Specification();
        specification.setRam(2);
        specification.setProcessor("Intel");
        specification.setInternalMemory(500);

        phone.setSpecification(specification);

        session.beginTransaction();
        session.save(phone);
        session.flush();
        session.clear();


        TypedQuery<Phone> phones = session.createQuery("from Phone as phone_ where phone_.specification.processor like :expr", Phone.class);
        phones.setParameter("expr","Intel");
        assertEquals(1, phones.getResultList().size());
        assertEquals("IPhone", phones.getResultList().get(0).getName());
        assertEquals(2, phones.getResultList().get(0).getSpecification().getRam());
    }

}