package com.baeldung.hibernate.pessimisticlocking;

import com.baeldung.hibernate.HibernateUtil;
import com.vividsolutions.jts.util.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.*;
import java.io.IOException;
import java.util.Arrays;

public class BasicPessimisticLockingIntegrationTest {

    @BeforeClass
    public static void setUp() throws IOException {
        EntityManager entityManager = getEntityManagerWithOpenTransaction();
        PessimisticLockingStudent student = new PessimisticLockingStudent(1L, "JOHN");
        PessimisticLockingCourse course = new PessimisticLockingCourse(1L, "MATH", student);
        student.setCourses(Arrays.asList(course));
        entityManager.persist(course);
        entityManager.persist(student);
        entityManager.getTransaction()
            .commit();
        entityManager.close();
    }

    @Test
    public void givenFoundRecordWithPessimisticRead_whenFindingNewOne_PessimisticLockExceptionThrown() {
        try {
            EntityManager entityManager = getEntityManagerWithOpenTransaction();
            entityManager.find(PessimisticLockingStudent.class, 1L, LockModeType.PESSIMISTIC_READ);

            EntityManager entityManager2 = getEntityManagerWithOpenTransaction();
            entityManager2.find(PessimisticLockingStudent.class, 1L, LockModeType.PESSIMISTIC_READ);

            entityManager.close();
            entityManager2.close();
        } catch (Exception e) {
            Assert.isTrue(e instanceof PessimisticLockException);
        }
    }

    @Test
    public void givenRecordWithPessimisticReadQuery_whenQueryingNewOne_PessimisticLockExceptionThrown() throws IOException {
        try {
            EntityManager entityManager = getEntityManagerWithOpenTransaction();
            Query query = entityManager.createQuery("from Student where studentId = :studentId");
            query.setParameter("studentId", 1L);
            query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
            query.getResultList();

            EntityManager entityManager2 = getEntityManagerWithOpenTransaction();
            Query query2 = entityManager2.createQuery("from Student where studentId = :studentId");
            query2.setParameter("studentId", 1L);
            query2.setLockMode(LockModeType.PESSIMISTIC_READ);
            query2.getResultList();

            entityManager.close();
            entityManager2.close();
        } catch (Exception e) {
            Assert.isTrue(e instanceof PessimisticLockException);
        }
    }

    @Test
    public void givenRecordWithPessimisticReadLock_whenFindingNewOne_PessimisticLockExceptionThrown() {
        try {
            EntityManager entityManager = getEntityManagerWithOpenTransaction();
            PessimisticLockingStudent resultStudent = entityManager.find(PessimisticLockingStudent.class, 1L);
            entityManager.lock(resultStudent, LockModeType.PESSIMISTIC_READ);

            EntityManager entityManager2 = getEntityManagerWithOpenTransaction();
            entityManager2.find(PessimisticLockingStudent.class, 1L, LockModeType.PESSIMISTIC_FORCE_INCREMENT);

            entityManager.close();
            entityManager2.close();
        } catch (Exception e) {
            Assert.isTrue(e instanceof PessimisticLockException);
        }
    }

    @Test
    public void givenRecordAndRefreshWithPessimisticRead_whenFindingWithPessimisticWrite_PessimisticLockExceptionThrown() {
        try {
            EntityManager entityManager = getEntityManagerWithOpenTransaction();
            PessimisticLockingStudent resultStudent = entityManager.find(PessimisticLockingStudent.class, 1L);
            entityManager.refresh(resultStudent, LockModeType.PESSIMISTIC_FORCE_INCREMENT);

            EntityManager entityManager2 = getEntityManagerWithOpenTransaction();
            entityManager2.find(PessimisticLockingStudent.class, 1L, LockModeType.PESSIMISTIC_WRITE);

            entityManager.close();
            entityManager2.close();
        } catch (Exception e) {
            Assert.isTrue(e instanceof PessimisticLockException);
        }
    }

    @Test
    public void givenRecordWithPessimisticRead_whenUpdatingRecord_PessimisticLockExceptionThrown() {
        try {
            EntityManager entityManager = getEntityManagerWithOpenTransaction();
            PessimisticLockingStudent resultStudent = entityManager.find(PessimisticLockingStudent.class, 1L);
            entityManager.refresh(resultStudent, LockModeType.PESSIMISTIC_READ);

            EntityManager entityManager2 = getEntityManagerWithOpenTransaction();
            PessimisticLockingStudent resultStudent2 = entityManager2.find(PessimisticLockingStudent.class, 1L);
            resultStudent2.setName("Change");
            entityManager2.persist(resultStudent2);
            entityManager2.getTransaction()
                .commit();

            entityManager.close();
            entityManager2.close();
        } catch (Exception e) {
            Assert.isTrue(e instanceof PessimisticLockException);
        }
    }

    @Test
    public void givenRecordWithPessimisticWrite_whenUpdatingRecord_PessimisticLockExceptionThrown() {
        try {
            EntityManager entityManager = getEntityManagerWithOpenTransaction();
            PessimisticLockingStudent resultStudent = entityManager.find(PessimisticLockingStudent.class, 1L);
            entityManager.refresh(resultStudent, LockModeType.PESSIMISTIC_WRITE);

            EntityManager entityManager2 = getEntityManagerWithOpenTransaction();
            PessimisticLockingStudent resultStudent2 = entityManager2.find(PessimisticLockingStudent.class, 1L);
            resultStudent2.setName("Change");
            entityManager2.persist(resultStudent2);
            entityManager2.getTransaction()
                .commit();

            entityManager.close();
            entityManager2.close();
        } catch (Exception e) {
            Assert.isTrue(e instanceof PessimisticLockException);
        }
    }

    protected static EntityManager getEntityManagerWithOpenTransaction() throws IOException {
        String propertyFileName = "hibernate-pessimistic-locking.properties";
        EntityManager entityManager = HibernateUtil.getSessionFactory(propertyFileName)
            .openSession();
        entityManager.getTransaction()
            .begin();

        return entityManager;
    }

}
