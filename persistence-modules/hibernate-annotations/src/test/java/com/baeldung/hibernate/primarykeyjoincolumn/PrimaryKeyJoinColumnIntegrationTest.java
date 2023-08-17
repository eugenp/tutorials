package com.baeldung.hibernate.primarykeyjoincolumn;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class PrimaryKeyJoinColumnIntegrationTest {

    private static EntityManagerFactory emf;

    private static EntityManager em;

    @BeforeAll
    public static void setup() {
        emf = Persistence.createEntityManagerFactory("com.baeldung.department_person");
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

    @AfterAll
    public static void teardown() {
        em.close();
        emf.close();
    }

    @Test
    void givenPersonEntity_getDepartment_shouldExist() {
        Person person = em.find(Person.class, 1L);
        assertNotNull(person);
        assertEquals("John Doe", person.getName());
        assertNotNull(person.getDepartment());
        assertEquals("IT", person.getDepartment().getName());
    }
}
