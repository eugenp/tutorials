package com.baeldung.hibernate.primarykeyjoincolumn;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PrimaryKeyJoinColumnIntegrationTest {

    private static EntityManagerFactory emf;

    private EntityManager em;

    @Before
    public void setup() {
        emf = Persistence.createEntityManagerFactory("com.baeldung.movie_catalog");
        em = emf.createEntityManager();
        em.getTransaction()
          .begin();
        Department department = new Department();
        department.setName("IT");
        em.persist(department);
        Person person = new Person();
        person.setName("John Doe");
        person.setDepartment(department);
        em.persist(person);
        em.getTransaction()
          .commit();
    }

    @After
    public void teardown() {
        em.close();
        emf.close();
    }

    @Test
    public void givenPersonEntity_getDepartment_shouldExist() {
        Person person = em.find(Person.class, 1L);
        assertNotNull(person);
        assertEquals("John Doe", person.getName());
        assertNotNull(person.getDepartment());
        assertEquals("IT", person.getDepartment()
         .getName());
    }
}
