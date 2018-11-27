package com.baeldung.hibernate.findall;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.hibernate.HibernateUtil;
import com.baeldung.hibernate.pojo.Student;

public class FindAllUnitTest {

    private Session session;
    private Transaction transaction;
    
    private FindAll findAll;

    @Before
    public void setUp() throws IOException {

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        findAll = new FindAll(session);

        session.createNativeQuery("delete from Student").executeUpdate();

        Student student1 = new Student();
        session.persist(student1);

        Student student2 = new Student();
        session.persist(student2);

        Student student3 = new Student();
        session.persist(student3);

        transaction.commit();
        transaction = session.beginTransaction();
    }

    @After
    public void tearDown() {
        transaction.rollback();
        session.close();
    }

    @Test
    public void givenCriteriaQuery_WhenFindAll_ThenGetAllPersons() {
        List<Student> list = findAll.findAllWithCriteriaQuery();
        assertEquals(3, list.size());
    }
    
    @Test
    public void givenJpql_WhenFindAll_ThenGetAllPersons() {
        List<Student> list = findAll.findAllWithJpql();
        assertEquals(3, list.size());
    }
}
