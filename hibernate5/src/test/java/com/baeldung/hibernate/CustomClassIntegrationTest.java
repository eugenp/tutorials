package com.baeldung.hibernate;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.TimeZone;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.hibernate.pojo.Result;

class CustomClassIntegrationTest {

    private Session session;

    private Transaction transaction;

    @BeforeEach
    public void setUp() throws IOException {
        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.createNativeQuery("delete from emp").executeUpdate();
        session.createNativeQuery("delete from dept").executeUpdate();
        transaction.commit();
        transaction = session.beginTransaction();
    }
    
    @Test
    public void whenAllEmployeesSelected_ThenObjectGraphReturned() {
        @SuppressWarnings("unchecked")
        Query<Object> query = session.createQuery("from Employee");
        List employees = query.list();
    }

    
    @Test
    public void whenResultConstructorInSelect_ThenListOfResultReturned() {
        Query query = session.createQuery("select new Result(e.name, e.department.name) from Employee e");
        List<Result> employees = query.list();
    }
}
