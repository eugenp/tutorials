package com.baeldung.jpa.index;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

public class IndexIntegrationTest {
    private static EntityManagerFactory factory;
    private static EntityManager entityManager;

    @BeforeClass
    public static void setup() {
        factory = Persistence.createEntityManagerFactory("jpa-index");
        entityManager = factory.createEntityManager();
    }

    @Test
    public void givenStudent_whenPersistStudentWithSameFirstName_thenConstraintViolationException() {
        Student student = new Student();
        student.setFirstName("FirstName");
        student.setLastName("LastName");

        Student student2 = new Student();
        student2.setFirstName("FirstName");
        student2.setLastName("LastName2");

        entityManager.getTransaction().begin();
        entityManager.persist(student);
        entityManager.getTransaction().commit();

        Assert.assertEquals(1L, (long) student.getId());

        entityManager.getTransaction().begin();
        try {
            entityManager.persist(student2);
            entityManager.getTransaction().commit();
            Assert.fail("Should raise an exception - unique key violation");
        } catch (Exception ex) {
            Assert.assertTrue(Optional.of(ex).map(Throwable::getCause).map(Throwable::getCause).filter(x -> x instanceof ConstraintViolationException).isPresent());
        } finally {
            entityManager.getTransaction().rollback();
        }
    }
}
