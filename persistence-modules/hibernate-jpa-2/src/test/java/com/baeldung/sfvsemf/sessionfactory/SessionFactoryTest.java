package com.baeldung.sfvsemf.sessionfactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

import com.baeldung.sfvsemf.entity.Person;

public class SessionFactoryTest {

    @Test
    void givenSessionFactory_whenPersistAndFind_thenAssertObjectPersisted() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Person person = new Person("John", "johndoe@email.com");
            session.persist(person);
            transaction.commit();

            Person persistedPerson = session.find(Person.class, person.getId());
            assertEquals(person.getName(), persistedPerson.getName());
            assertEquals(person.getEmail(), persistedPerson.getEmail());
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            session.close();
            sessionFactory.close();
        }

    }

}
