package com.baeldung.sfvsemf.entitymanagerfactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.baeldung.sfvsemf.entity.Person;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerFactoryTest {

    @Test
    void givenEntityManagerFactory_whenPersistAndFind_thenAssertObjectPersisted() {
        EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("com.baeldung.sfvsemf.persistence_unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            Person person = new Person("John", "johndoe@email.com");
            entityManager.persist(person);
            entityManager.getTransaction().commit();

            Person persistedPerson = entityManager.find(Person.class, person.getId());
            assertEquals(person.getName(), persistedPerson.getName());
            assertEquals(person.getEmail(), persistedPerson.getEmail());
        } catch (Exception ex) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
            entityManagerFactory.close();
        }
    }

}
