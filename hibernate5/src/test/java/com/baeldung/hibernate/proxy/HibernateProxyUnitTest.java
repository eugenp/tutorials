package com.baeldung.hibernate.proxy;

import org.hibernate.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.fail;

public class HibernateProxyUnitTest {

    private Session session;

    @Before
    public void init(){
        try {
            session = HibernateUtil.getSessionFactory("hibernate.properties")
                    .openSession();
        } catch (HibernateException | IOException e) {
            fail("Failed to initiate Hibernate Session [Exception:" + e.toString() + "]");
        }

        Boss boss = new Boss("Eduard", "Freud");
        session.save(boss);
    }

    @After
    public void close(){
        if(session != null) {
            session.close();
        }
    }

    @Test(expected = NullPointerException.class)
    public void givenAnInexistentEmployeeId_whenUseGetMethod_thenReturnNull() {
        Employee employee = session.get(Employee.class, new Long(14));
        assertNull(employee);
        employee.getId();
    }

    @Test
    public void givenAnInexistentEmployeeId_whenUseLoadMethod_thenReturnAProxy() {
        Employee employee = session.load(Employee.class, new Long(14));
        assertNotNull(employee);
    }

    @Test
    public void givenABatchEmployeeList_whenSaveOne_thenSaveTheWholeBatch() {
        Transaction transaction = session.beginTransaction();

        for (long i = 1; i <= 5; i++) {
            Employee employee = new Employee();
            employee.setName("Employee " + i);
            session.save(employee);
        }

        //After this line is possible to see all the insertions in the logs
        session.flush();
        session.clear();
        transaction.commit();

        transaction = session.beginTransaction();

        List<Employee> employeeList = session.createQuery("from Employee")
                                        .setCacheMode(CacheMode.IGNORE).getResultList();

        assertEquals(employeeList.size(), 5);
        transaction.commit();
    }
}
