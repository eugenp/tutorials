package com.baeldung.jpa.uniqueconstraints;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UniqueConstraintIntegrationTest {
    private static EntityManagerFactory factory;
    private static EntityManager entityManager;

    @BeforeAll
    public static void setup() {
        factory = Persistence.createEntityManagerFactory("jpa-unique-constraints");
        entityManager = factory.createEntityManager();
    }

    @Test
    public void whenPersistPersonWithSameNumberAndStatus_thenConstraintViolationException() {
        Person person1 = new Person();
        person1.setPersonNumber(12345L);
        person1.setIsActive(Boolean.TRUE);

        Person person2 = new Person();
        person2.setPersonNumber(12345L);
        person2.setIsActive(Boolean.TRUE);

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
    public void whenPersistPersonWithSameSCodeAndDecode_thenConstraintViolationException() {
        Person person1 = new Person();
        person1.setDcode("Sec1");
        person1.setScode("Axybg356");

        Person person2 = new Person();
        person2.setDcode("Sec1");
        person2.setScode("Axybg356");

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
    public void whenPersistPersonWithSameNumberAndAddress_thenConstraintViolationException() {
        Address address1 = new Address();
        address1.setStreetAddress("40 Street");

        Person person1 = new Person();
        person1.setPersonNumber(54321L);
        person1.setAddress(address1);

        Person person2 = new Person();
        person2.setPersonNumber(99999L);
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