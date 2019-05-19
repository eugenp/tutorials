package com.baeldung.jpa.basicannotation;

import org.hibernate.PropertyValueException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.IOException;

public class BasicAnnotationIntegrationTest {

    private static EntityManager entityManager;
    private static EntityManagerFactory entityManagerFactory;

    @BeforeClass
    public void setup() {
        entityManagerFactory = Persistence.createEntityManagerFactory("java-jpa-scheduled-day");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @Test
    public void givenACourse_whenCourseNamePresent_shouldPersist() {
        Course course = new Course();
        course.setName("Computers");

        entityManager.persist(course);
        entityManager.flush();
        entityManager.clear();

    }

    @Test(expected = PropertyValueException.class)
    public void givenACourse_whenCourseNameAbsent_shouldFail() {
        Course course = new Course();

        entityManager.persist(course);
        entityManager.flush();
        entityManager.clear();
    }

    @AfterClass
    public void destroy() {

        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
