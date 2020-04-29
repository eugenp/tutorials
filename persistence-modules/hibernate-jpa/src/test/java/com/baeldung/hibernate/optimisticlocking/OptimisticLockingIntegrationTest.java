package com.baeldung.hibernate.optimisticlocking;

import java.io.IOException;
import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;

import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.hibernate.HibernateUtil;

public class OptimisticLockingIntegrationTest {
    
    private static SessionFactory sessionFactory;

    @Before
    public void setUp() throws IOException {
        EntityManager entityManager = getEntityManagerWithOpenTransaction();
        OptimisticLockingCourse course = new OptimisticLockingCourse(1L, "MATH");
        OptimisticLockingStudent student = new OptimisticLockingStudent(1L, "John", "Doe", Arrays.asList(course));
        course.setStudent(student);
        entityManager.persist(course);
        entityManager.persist(student);
        entityManager.getTransaction()
            .commit();
        entityManager.close();
    }

    @After
    public void clean() throws IOException {
        EntityManager entityManager = getEntityManagerWithOpenTransaction();
        OptimisticLockingCourse course = entityManager.find(OptimisticLockingCourse.class, 1L);
        OptimisticLockingStudent student = entityManager.find(OptimisticLockingStudent.class, 1L);
        entityManager.remove(course);
        entityManager.remove(student);
        entityManager.getTransaction()
            .commit();
        entityManager.close();
    }

    @Test(expected = OptimisticLockException.class)
    public void givenVersionedEntities_whenConcurrentUpdate_thenOptimisticLockException() throws IOException {
        EntityManager em = getEntityManagerWithOpenTransaction();
        OptimisticLockingStudent student = em.find(OptimisticLockingStudent.class, 1L);

        EntityManager em2 = getEntityManagerWithOpenTransaction();
        OptimisticLockingStudent student2 = em2.find(OptimisticLockingStudent.class, 1L);
        student2.setName("RICHARD");
        em2.persist(student2);
        em2.getTransaction()
            .commit();
        em2.close();

        student.setName("JOHN");
        em.persist(student);
        em.getTransaction()
            .commit();
        em.close();
    }

    @Test(expected = OptimisticLockException.class)
    public void givenVersionedEntitiesWithLockByFindMethod_whenConcurrentUpdate_thenOptimisticLockException() throws IOException {
        EntityManager em = getEntityManagerWithOpenTransaction();
        OptimisticLockingStudent student = em.find(OptimisticLockingStudent.class, 1L, LockModeType.OPTIMISTIC);

        EntityManager em2 = getEntityManagerWithOpenTransaction();
        OptimisticLockingStudent student2 = em2.find(OptimisticLockingStudent.class, 1L, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        student2.setName("RICHARD");
        em2.persist(student2);
        em2.getTransaction()
            .commit();
        em2.close();

        student.setName("JOHN");
        em.persist(student);
        em.getTransaction()
            .commit();
        em.close();
    }

    @Test(expected = OptimisticLockException.class)
    public void givenVersionedEntitiesWithLockByRefreshMethod_whenConcurrentUpdate_thenOptimisticLockException() throws IOException {
        EntityManager em = getEntityManagerWithOpenTransaction();
        OptimisticLockingStudent student = em.find(OptimisticLockingStudent.class, 1L);
        em.refresh(student, LockModeType.OPTIMISTIC);

        EntityManager em2 = getEntityManagerWithOpenTransaction();
        OptimisticLockingStudent student2 = em2.find(OptimisticLockingStudent.class, 1L);
        em.refresh(student, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        student2.setName("RICHARD");
        em2.persist(student2);
        em2.getTransaction()
            .commit();
        em2.close();

        student.setName("JOHN");
        em.persist(student);
        em.getTransaction()
            .commit();
        em.close();
    }

    @Test(expected = OptimisticLockException.class)
    public void givenVersionedEntitiesWithLockByLockMethod_whenConcurrentUpdate_thenOptimisticLockException() throws IOException {
        EntityManager em = getEntityManagerWithOpenTransaction();
        OptimisticLockingStudent student = em.find(OptimisticLockingStudent.class, 1L);
        em.lock(student, LockModeType.OPTIMISTIC);

        EntityManager em2 = getEntityManagerWithOpenTransaction();
        OptimisticLockingStudent student2 = em2.find(OptimisticLockingStudent.class, 1L);
        em.lock(student, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        student2.setName("RICHARD");
        em2.persist(student2);
        em2.getTransaction()
            .commit();
        em2.close();

        student.setName("JOHN");
        em.persist(student);
        em.getTransaction()
            .commit();
        em.close();
    }

    protected static EntityManager getEntityManagerWithOpenTransaction() throws IOException {
        String propertyFileName = "hibernate-pessimistic-locking.properties";
        if (sessionFactory == null) {
            sessionFactory = HibernateUtil.getSessionFactory(propertyFileName);
        }
        EntityManager entityManager = sessionFactory.openSession();
        entityManager.getTransaction().begin();

        return entityManager;
    }
    
    @AfterClass
    public static void afterTests() {
        sessionFactory.close();
    }
}
