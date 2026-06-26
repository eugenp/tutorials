package com.baeldung.hibernate.embeddedtable;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class EmbeddedTableIntegrationTest {
    
    private static SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;
    
    @BeforeAll
    static void initSessionFactory() {
        sessionFactory = HibernateConfigUtils.sessionFactory();
    }

    @AfterAll
    static void destroySessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @BeforeEach
    void setUp() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @AfterEach
    void tearDown() {
        if (transaction != null && transaction.isActive()) {
            transaction.rollback();
        }

        if (session != null && session.isOpen()) {
            session.close();
        }
    }
    
    @Test
    void whenUsingEmbeddedTableThenMapIntoTwoSeparateTables() {
        Address address = new Address();
        address.setStreet("12 Av. Tamassint center");
        address.setCity("Tamassint");
        address.setCode("10000");
        
        Person person = new Person();
        person.setId(1);
        person.setFirstName("Azhrioun");
        person.setLastName("Abderrahim");
        person.setAddress(address);
        
        session.persist(person);
        session.flush();
        
        Object[] addressRow = (Object[]) session.createNativeQuery("select street, city, code from person_address", Object[].class)
            .uniqueResult();
        
        assertEquals("12 Av. Tamassint center", addressRow[0]);
        assertEquals("Tamassint", addressRow[1]);
        assertEquals("10000", addressRow[2]);
        
        Object[] personRow = (Object[]) session.createNativeQuery("select id, first_name, last_name from person", Object[].class)
            .uniqueResult();
        
        assertEquals(1, personRow[0]);
        assertEquals("Azhrioun", personRow[1]);
        assertEquals("Abderrahim", personRow[2]);
    }

}
