package com.baeldung.hibernate.scalarmethod;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.hibernate.HibernateUtil;
import com.baeldung.hibernate.pojo.Student;

public class HibernateScalarExampleUnitTest {

    private static Session session;
    private static Transaction transaction;
    private static final int DATA_SIZE = 50000;
    private static HibernateScalarExample scalarExample;

    @BeforeClass
    public static void setUp() throws IOException {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        scalarExample = new HibernateScalarExample(session);
        session.createNativeQuery("delete from Student").executeUpdate();
        for (int i = 0; i < DATA_SIZE; i++) {
            Student student = new Student("John-" + i, generateRandomAge(5, 24));
            session.persist(student);
        }
        transaction.commit();
        transaction = session.beginTransaction();
    }

    @AfterClass
    public static void tearDown() {
        transaction.rollback();
        session.close();
    }

    @Test
    public void givenNativeQuery_whenNoScalarUsed_ThenFetchAll() {
        List<Object[]> list = scalarExample.fetchColumnWithNativeQuery();
        assertEquals(DATA_SIZE, list.size());
    }

    @Test
    public void givenNativeQuery_whenScalarUsed_ThenFetchAll() {
        List<Object[]> list = scalarExample.fetchColumnWithScalar();
        assertEquals(DATA_SIZE, list.size());
    }

    @Test
    public void givenNativeQuery_whenScalarUsed_ThenFetchLimitedColumns() {
        List<String> list = scalarExample.fetchLimitedColumnWithScalar();
        for (String colValue : list) {
            assertTrue(colValue.startsWith("John"));
        }
    }

    @Test
    public void givenNativeQuery_whenScalarUsedForSingleResult_ThenSingleValueReturned() {
        List<Object[]> list = scalarExample.fetchColumnWithOverloadedScalar();
        for (Object[] colArray : list) {
            assertEquals(2, colArray.length);
        }
    }

    @Test
    public void whenScalarUsedForAvgAge_ThenSingleValueReturned() {
        Double avgAge = scalarExample.fetchAvgAgeWithScalar();
        assertEquals(true, (avgAge >= 5 && avgAge <= 24));
    }

    private static int generateRandomAge(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }
}
