package com.baeldung.hibernate.pessimisticlocking;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.io.IOException;

public class PessimisticLockingForVersionedEntitiesTest extends PessimisticLockingBaseTest {

    private static VersionedStudent student = new VersionedStudent(1, "John", "Doe");

    @BeforeClass
    public static void setUp() throws IOException {
        EntityManager entityManager = getEntityManagerWithOpenTransaction();
        entityManager.persist(student);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void givenFoundRecordWithPessimisticRead_whenFindingNewOne_PessimisticLockExceptionThrown() throws IOException {
        EntityManager entityManager = getEntityManagerWithOpenTransaction();
        VersionedStudent versionedStudent = entityManager.find(VersionedStudent.class, this.student.getStudentId());

        EntityManager entityManager2 = getEntityManagerWithOpenTransaction();
        VersionedStudent result = entityManager2.find(VersionedStudent.class, this.student.getStudentId(), LockModeType.PESSIMISTIC_FORCE_INCREMENT);
        result.setLastName("ChangedLastName");
        entityManager2.getTransaction().commit();

        EntityManager entityManager3 = getEntityManagerWithOpenTransaction();
        entityManager2.find(Student.class, this.student.getStudentId());
        VersionedStudent versionedStudent2 = entityManager3.find(VersionedStudent.class, this.student.getStudentId());

        entityManager.close();
        entityManager2.close();
        entityManager3.close();

        Assert.assertNotEquals(versionedStudent2.getVersion(), versionedStudent.getVersion());
    }

}
