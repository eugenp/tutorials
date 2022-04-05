package com.baeldung.hibernate.aggregatefunctions;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.hibernate.HibernateUtil;
import com.baeldung.hibernate.pojo.Student;

public class AggregateFunctionsIntegrationTest {

    private static Session session;
    private static Transaction transaction;

    @BeforeClass
    public static final void setup() throws HibernateException, IOException {
        session = HibernateUtil.getSessionFactory()
            .openSession();
        transaction = session.beginTransaction();

        Student jonas = new Student("Jonas", 22);
        session.save(jonas);

        Student sally = new Student("Sally", 20);
        session.save(sally);

        Student simon = new Student("Simon", 25);
        session.save(simon);

        Student raven = new Student("Raven", 21);
        session.save(raven);

        Student sam = new Student("Sam", 23);
        session.save(sam);

    }

    @AfterClass
    public static final void teardown() {
        if (session != null) {
            transaction.rollback();
            session.close();
        }
    }

    @Test
    public void whenMaxAge_ThenReturnValue() {
        int maxAge = (int) session.createQuery("SELECT MAX(age) from Student")
            .getSingleResult();
        assertThat(maxAge).isEqualTo(25);
    }

    @Test
    public void whenMinAge_ThenReturnValue() {
        int minAge = (int) session.createQuery("SELECT MIN(age) from Student")
            .getSingleResult();
        assertThat(minAge).isEqualTo(20);
    }

    @Test
    public void whenAverageAge_ThenReturnValue() {
        Double avgAge = (Double) session.createQuery("SELECT AVG(age) from Student")
            .getSingleResult();
        assertThat(avgAge).isEqualTo(22.2);
    }

    @Test
    public void whenCountAll_ThenReturnValue() {
        Long totalStudents = (Long) session.createQuery("SELECT COUNT(*) from Student")
            .getSingleResult();
        assertThat(totalStudents).isEqualTo(5);
    }

    @Test
    public void whenSumOfAllAges_ThenReturnValue() {
        Long sumOfAllAges = (Long) session.createQuery("SELECT SUM(age) from Student")
            .getSingleResult();
        assertThat(sumOfAllAges).isEqualTo(111);
    }
}
