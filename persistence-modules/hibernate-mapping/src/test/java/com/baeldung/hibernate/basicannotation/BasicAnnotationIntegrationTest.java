package com.baeldung.hibernate.basicannotation;

import com.baeldung.hibernate.HibernateUtil;
import com.baeldung.hibernate.basicannotation.Course;
import com.baeldung.hibernate.Strategy;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.hibernate.SessionFactory;
import org.junit.BeforeClass;

import java.io.IOException;

public class BasicAnnotationIntegrationTest {

    private static SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @BeforeClass
    public static void beforeTests() {
        sessionFactory = HibernateUtil.getSessionFactory(Strategy.MAP_KEY_COLUMN_BASED);
    }

    @Before
    public void setUp() throws IOException {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void tearDown() {
        transaction.rollback();
        session.close();
    }

    @Test
    public void givenACourse_whenCourseNamePresent_shouldPersist() {
        Course course = new Course();
        course.setName("Computers");

        session.save(course);
        session.flush();
        session.clear();

    }

    @Test(expected = PropertyValueException.class)
    public void givenACourse_whenCourseNameAbsent_shouldFail() {
        Course course = new Course();

        session.save(course);
        session.flush();
        session.clear();
    }
}
