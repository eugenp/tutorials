package com.baeldung.hibernate;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.TimeZone;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class CustomClassIntegrationTest {

    private Session session;

    private Transaction transaction;

    @Before
    public void setUp() throws IOException {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.createNativeQuery("delete from employee").executeUpdate();
        session.createNativeQuery("delete from department").executeUpdate();
        transaction.commit();
    }
    
    @Test
    void whenAllEmployeesSelected_ThenObjectGraphReturned() {
        Query query = session.createQuery("from employee");
        List employees = query.list();
    }

}
