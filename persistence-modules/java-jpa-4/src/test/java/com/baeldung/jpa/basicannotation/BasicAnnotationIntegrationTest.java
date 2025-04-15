package com.baeldung.jpa.basicannotation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BasicAnnotationIntegrationTest {

    private static EntityManager entityManager;
    private static EntityManagerFactory entityManagerFactory;

    @BeforeClass
    public static void setup() {
        entityManagerFactory = Persistence.createEntityManagerFactory("java-jpa-scheduled-day");
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    @Test
    public void givenACourse_whenCourseNamePresent_shouldPersist() {
        Course course = new Course();
        course.setName("Computers");

        entityManager.persist(course);
        entityManager.flush();
        entityManager.clear();

    }

    @Test(expected = PersistenceException.class)
    public void givenACourse_whenCourseNameAbsent_shouldFail() {
        Course course = new Course();

        entityManager.persist(course);
        entityManager.flush();
        entityManager.clear();
    }

    @AfterClass
    public static void destroy() {

        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
