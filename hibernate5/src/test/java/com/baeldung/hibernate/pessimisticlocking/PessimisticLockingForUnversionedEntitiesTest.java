package com.baeldung.hibernate.pessimisticlocking;

import com.vividsolutions.jts.util.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class PessimisticLockingForUnversionedEntitiesTest extends PessimisticLockingBaseTest {

    private static Student student = new Student(1, "John", "Doe");

    @BeforeClass
    public static void setUp() throws IOException {
        EntityManager entityManager = getEntityManagerWithOpenTransaction();
        entityManager.persist(student);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Test
    public void givenFoundRecordWithPessimisticRead_whenFindingNewOne_PessimisticLockExceptionThrown() {
        try {
            EntityManager entityManager = getEntityManagerWithOpenTransaction();
            entityManager.find(Student.class, this.student.getStudentId(), LockModeType.PESSIMISTIC_READ);

            EntityManager entityManager2 = getEntityManagerWithOpenTransaction();
            entityManager2.find(Student.class, this.student.getStudentId(), LockModeType.PESSIMISTIC_READ);

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
            Integer studentId = student.getStudentId();
            Query query = entityManager.createQuery("from Student where studentId = :studentId");
            query.setParameter("studentId", studentId);
            query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
            query.getResultList();

            EntityManager entityManager2 = getEntityManagerWithOpenTransaction();
            Query query2 = entityManager2.createQuery("from Student where studentId = :studentId");
            query2.setParameter("studentId", student.getStudentId());
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
            Integer studentId = this.student.getStudentId();
            Student resultStudent = entityManager.find(Student.class, studentId);
            entityManager.lock(resultStudent, LockModeType.PESSIMISTIC_READ);

            EntityManager entityManager2 = getEntityManagerWithOpenTransaction();
            entityManager2.find(Student.class, this.student.getStudentId(), LockModeType.PESSIMISTIC_FORCE_INCREMENT);

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
            Integer studentId = this.student.getStudentId();
            Student resultStudent = entityManager.find(Student.class, studentId);
            entityManager.refresh(resultStudent, LockModeType.PESSIMISTIC_FORCE_INCREMENT);

            EntityManager entityManager2 = getEntityManagerWithOpenTransaction();
            entityManager2.find(Student.class, this.student.getStudentId(), LockModeType.PESSIMISTIC_WRITE);

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
            Student resultStudent = entityManager.find(Student.class, this.student.getStudentId());
            entityManager.refresh(resultStudent, LockModeType.PESSIMISTIC_READ);

            EntityManager entityManager2 = getEntityManagerWithOpenTransaction();
            Student resultStudent2 = entityManager2.find(Student.class, this.student.getStudentId());
            resultStudent2.setName("Test");
            resultStudent2.setLastName("Test");
            entityManager2.persist(resultStudent2);
            entityManager2.getTransaction().commit();

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
            Student resultStudent = entityManager.find(Student.class, this.student.getStudentId());
            entityManager.refresh(resultStudent, LockModeType.PESSIMISTIC_WRITE);

            EntityManager entityManager2 = getEntityManagerWithOpenTransaction();
            Student resultStudent2 = entityManager2.find(Student.class, this.student.getStudentId());
            resultStudent2.setName("Test");
            resultStudent2.setLastName("Test");
            entityManager2.persist(resultStudent2);
            entityManager2.getTransaction().commit();

            entityManager.close();
            entityManager2.close();
        } catch (Exception e) {
            Assert.isTrue(e instanceof PessimisticLockException);
        }
    }

}
