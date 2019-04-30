package com.baeldung.hibernate.basicannotation;

import com.baeldung.hibernate.HibernateUtil;
import com.baeldung.hibernate.pojo.Course;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class BasicAnnotationIntegrationTest {

    private Session session;
    private Transaction transaction;

    @Before
    public void setUp() throws IOException {
        session = HibernateUtil.getSessionFactory().openSession();
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
