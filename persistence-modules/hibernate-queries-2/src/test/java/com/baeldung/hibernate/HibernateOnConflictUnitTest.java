package com.baeldung.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.hibernate.pojo.Student;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class HibernateOnConflictUnitTest {

    private static Session session;
    private static Transaction transaction;

    @BeforeClass
    public static void setUp() throws IOException {
        session = HibernateUtil.getSessionFactory()
            .openSession();
        transaction = session.beginTransaction();

        session.createMutationQuery("delete from Student")
            .executeUpdate();

        Student student = new Student("John");
        session.persist(student);

        transaction.commit();
        transaction = session.beginTransaction();
    }

    @AfterClass
    public static void tearDown() {
        transaction.rollback();
        session.close();
    }

    @Test
    public void givenInsertQueryWithOnConflictClause_whenNoConflict_ThenInsertNewRecord() {

        long rowCountBefore = getRowCount();
        int updated = session.createMutationQuery("""
            insert into Student (studentId, name) values (2, 'Sean') 
            on conflict(studentId) do update 
            set name = excluded.name
            """).executeUpdate();
        long rowCountAfter = getRowCount();

        assertEquals(updated, 1);
        assertEquals(rowCountBefore, rowCountAfter);
    }

    @Test
    public void givenInsertQueryWithOnConflictClause_whenConflictOccurs_ThenUpdateExistingRecord() {

        long rowCountBefore = getRowCount();
        int updated = session.createMutationQuery("""
            insert into Student (studentId, name) values (1, 'Sean') 
            on conflict(studentId) do update 
            set name = excluded.name
            """)
            .executeUpdate();
        long rowCountAfter = getRowCount();

        assertEquals(updated, 1);
        assertEquals(rowCountBefore, rowCountAfter);
    }

    @Test
    public void givenInsertQueryWithOnConflictClause_whenNoConflcitDoNothing_ThenInsertNewRecord() {
        long rowCountBefore = getRowCount();
        int updated = session.createMutationQuery("""
            insert into Student (studentId, name) values (2, 'Sean') 
            on conflict do nothing
            """)
            .executeUpdate();
        long rowCountAfter = getRowCount();

        assertEquals(updated, 1);
        assertNotEquals(rowCountBefore, rowCountAfter);
    }

    @Test
    public void givenInsertQueryWithOnConflictClause_whenOnConflcitDoNothing_ThenErrorIsLogged() {
        long rowCountBefore = getRowCount();
        int updated = session.createMutationQuery("""
            insert into Student (studentId, name) values (1, 'Sean') 
            on conflict do nothing
            """)
            .executeUpdate();
        long rowCountAfter = getRowCount();
        assertEquals(updated, 0);
        assertEquals(rowCountBefore, rowCountAfter);
    }

    private static long getRowCount() {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
        Root<Student> root = query.from(Student.class);
        query.select(criteriaBuilder.count(root));
        return session.createQuery(query)
            .getSingleResult();
    }
}
