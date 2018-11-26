package com.baeldung.hibernate.onetoone;

import com.baeldung.hibernate.onetoone.jointablebased.Employee;
import com.baeldung.hibernate.onetoone.jointablebased.WorkStation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HibernateOneToOneAnnotationJTBasedIntegrationTest {

    private static SessionFactory sessionFactory;

    private Session session;

    @BeforeClass
    public static void beforeTests() {
        sessionFactory = HibernateUtil.getSessionFactory(Strategy.JOIN_TABLE_BASED);
    }

    @Before
    public void setUp() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @Test
    public void givenData_whenInsert_thenCreates1to1relationship() {
        Employee employee = new Employee();
        employee.setName("bob@baeldung.com");

        WorkStation workStation = new WorkStation();
        workStation.setWorkstationNumber(626);
        workStation.setFloor("Sixth Floor");


        employee.setWorkStation(workStation);
        workStation.setEmployee(employee);

        session.persist(employee);
    }

    @After
    public void tearDown() {
        session.getTransaction()
                .commit();
        session.close();
    }

    @Test
    public void givenSession_whenRead_thenReturns1to1data() {
        @SuppressWarnings("unchecked")
        List<Employee> employees = session.createQuery("FROM Employee").list();
        assertNotNull(employees);
        for (Employee employee : employees) {
            assertEquals("bob@baeldung.com", employee.getName());
            assertNotNull(employee.getWorkStation());
            assertEquals((long) 626, (long) employee.getWorkStation().getWorkstationNumber());
            assertEquals("Sixth Floor", employee.getWorkStation().getFloor());

        }
    }

    @AfterClass
    public static void afterTests() {
        sessionFactory.close();
    }
}
