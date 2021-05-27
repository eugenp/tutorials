package com.baeldung.jpa.uniqueconstraints;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UniqueColumnIntegrationTest {
    
    private static EntityManagerFactory factory;
    private static EntityManager entityManager;

    @BeforeAll
    public static void setup() {
        factory = Persistence.createEntityManagerFactory("jpa-unique-constraints");
        entityManager = factory.createEntityManager();
    }

    @Test
    public void whenPersistPersonWithSameNumber_thenConstraintViolationException() {
        Person person1 = new Person();
        person1.setPersonNumber(2000L);
        person1.setEmail("john.beth@gmail.com");

        Person person2 = new Person();
        person2.setPersonNumber(2000L);
        person2.setEmail("anthony.green@gmail.com");

        entityManager.getTransaction().begin();
        entityManager.persist(person1);
        entityManager.getTransaction().commit();
        
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(person2);
            entityManager.getTransaction().commit();
            Assert.fail("Should raise an exception - unique key violation");
        } catch (Exception ex) {
            Assert.assertTrue(Optional.of(ex)
                .map(Throwable::getCause)
                .map(Throwable::getCause)
                .filter(x -> x instanceof ConstraintViolationException)
                .isPresent());
        } finally {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    public void whenPersistPersonWithSameEmail_thenConstraintViolationException() {
        Person person1 = new Person();
        person1.setPersonNumber(4000L);
        person1.setEmail("timm.beth@gmail.com");

        Person person2 = new Person();
        person2.setPersonNumber(3000L);
        person2.setEmail("timm.beth@gmail.com");

        entityManager.getTransaction().begin();
        entityManager.persist(person1);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        try {
            entityManager.persist(person2);
            entityManager.getTransaction().commit();
            Assert.fail("Should raise an exception - unique key violation");
        } catch (Exception ex) {
            Assert.assertTrue(Optional.of(ex)
                .map(Throwable::getCause)
                .map(Throwable::getCause)
                .filter(x -> x instanceof ConstraintViolationException)
                .isPresent());
        } finally {
            entityManager.getTransaction().rollback();
        }
    }

    @Test
    public void whenPersistPersonWithSameAddress_thenConstraintViolationException() {
        Person person1 = new Person();
        person1.setPersonNumber(5000L);
        person1.setEmail("chris.beck@gmail.com");

        Address address1 = new Address();
        address1.setStreetAddress("20 Street");
        person1.setAddress(address1);

        Person person2 = new Person();
        person2.setPersonNumber(6000L);
        person2.setEmail("mark.jonson@gmail.com");
        person2.setAddress(address1);

        entityManager.getTransaction().begin();
        entityManager.persist(person1);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        try {
            entityManager.persist(person2);
            entityManager.getTransaction().commit();
            Assert.fail("Should raise an exception - unique key violation");
        } catch (Exception ex) {
            Assert.assertTrue(Optional.of(ex)
                .map(Throwable::getCause)
                .map(Throwable::getCause)
                .filter(x -> x instanceof ConstraintViolationException)
                .isPresent());
        } finally {
            entityManager.getTransaction().rollback();
        }
    }
}