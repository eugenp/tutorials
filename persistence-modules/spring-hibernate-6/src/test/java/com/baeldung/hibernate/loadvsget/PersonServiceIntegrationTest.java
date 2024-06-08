package com.baeldung.hibernate.loadvsget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hibernate.LazyInitializationException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleObjectStateException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.persistence.model.Person;
import com.baeldung.spring.PersistenceConfig;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;

@Transactional
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class PersonServiceIntegrationTest {
    @Autowired
    private SessionFactory sessionFactory;

    @BeforeEach
    public void setUp() {
        // Clear database before each test
        Session session = sessionFactory.getCurrentSession();
        session.createNativeQuery("drop table if exists Person").executeUpdate();
        session.createNativeQuery("CREATE TABLE Person (id INT AUTO_INCREMENT, version integer not null, name VARCHAR(255), age INT, PRIMARY KEY (id))").executeUpdate();
    }

    @Test
    @Transactional
    public void givenExistPerson_whenUsingGet_thenReturnThePerson() {
        Person person = new Person("John Doe", 30);

        Session session = sessionFactory.openSession();
        session.saveOrUpdate(person);
        Person entity = session.get(Person.class, person.getId());

        assertNotNull(entity);
        assertEquals(person.getName(), entity.getName());
        assertEquals(person.getAge(), entity.getAge());
    }

    @Test
    @Transactional
    public void givenExistPerson_whenUsingLoad_thenReturnThePerson() {
        Person person = new Person("John Doe", 30);

        Session session = sessionFactory.openSession();
        session.saveOrUpdate(person);
        Person entity = session.load(Person.class, person.getId());
        assertNotNull(entity);

        assertEquals(person.getName(), entity.getName());
        assertEquals(person.getAge(), entity.getAge());
    }

    @Test
    public void givenExistPerson_whenUsingGetAndAfterSessionClose_thenReturnThePersonObject() {
        Person person = new Person("John Doe", 30);

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(person);
        session.getTransaction().commit();
        session.close();

        session = sessionFactory.openSession();
        Person entity = session.get(Person.class, person.getId());
        session.close();

        assertEquals(person.getName(), entity.getName());
        assertEquals(person.getAge(), entity.getAge());
    }

    @Test
    public void givenExistPerson_whenUsingLoadAndAfterSessionClose_thenTrowsLazyInitializationException() {
        Person person = new Person("John Doe", 30);

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(person);
        session.getTransaction().commit();
        session.close();

        session = sessionFactory.openSession();
        Person entity = session.load(Person.class, person.getId());
        session.close();

        assertThrows(LazyInitializationException.class, () -> {
            entity.getName();
        });
    }

    @Test
    @Transactional
    public void givenNotExistPerson_whenUsingGet_thenReturnNull() {
        Session session = sessionFactory.getCurrentSession();
        Person entity = session.get(Person.class, 1L);
        assertNull(entity);
    }

    @Test
    @Transactional
    public void givenNotExistPerson_whenUsingLoad_thenThrowsObjectNotFoundException() {
        // Attempt to retrieve a non-existent person using the load method
        Session session = sessionFactory.getCurrentSession();
        Person entity = session.load(Person.class, 1L);

        // Access properties of the entity, triggering the database query
        assertThrows(ObjectNotFoundException.class, () -> {
            entity.getName();
        });
    }

    @Test
    @Transactional
    public void givenExistPersonObjectInCache_whenUsingGet_thenReturnObjectInCache() {
        Person person = new Person("John Doe", 30);

        Session session = sessionFactory.openSession();
        session.saveOrUpdate(person);
        Person entity = session.get(Person.class, person.getId());
        Person cacheEntity = session.get(Person.class, person.getId());
    }

    @Test
    @Transactional
    public void givenNotExistPersonObjectInCache_whenUsingGet_thenReturnObjectInDB() {
        Person person = new Person("John Doe", 30);

        Session session = sessionFactory.openSession();
        session.saveOrUpdate(person);
        Person entity = session.get(Person.class, person.getId());
        session.evict(entity);
        Person cacheEntity = session.get(Person.class, person.getId());

    }

    @Test
    @Transactional
    public void givenExistPersonObjectInCache_whenUsingLoad_thenReturnObjectInCache() {
        Person person = new Person("John Doe", 30);

        Session session = sessionFactory.openSession();
        session.saveOrUpdate(person);
        Person entity = session.load(Person.class, person.getId());
        Person cacheEntity = session.load(Person.class, person.getId());
    }

    @Test
    @Transactional
    public void givenNotExistPersonObjectInCache_whenUsingLoad_thenReturnObjectInDB() {
        Person person = new Person("John Doe", 30);

        Session session = sessionFactory.openSession();
        session.saveOrUpdate(person);
        Person entity = session.load(Person.class, person.getId());
        session.evict(entity);
        Person cacheEntity = session.load(Person.class, person.getId());
    }
}
