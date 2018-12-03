package com.baeldung.hibernate;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.hibernate.entities.Department;
import com.baeldung.hibernate.entities.DeptEmployee;

public class NamedQueryIntegrationTest {
    private static Session session;

    private Transaction transaction;
    
    @BeforeClass
    public static void setUpClass() throws IOException {
        session = HibernateUtil.getSessionFactory("hibernate-namedquery.properties").openSession();
    }

    @Before
    public void setUp() throws IOException {
        transaction = session.beginTransaction();
        session.createNativeQuery("delete from deptemployee").executeUpdate();
        session.createNativeQuery("delete from department").executeUpdate();
        Department department = new Department("Sales");
        DeptEmployee employee1 = new DeptEmployee("John Wayne", "001", department);
        DeptEmployee employee2 = new DeptEmployee("Sarah Vinton", "002", department);
        DeptEmployee employee3 = new DeptEmployee("Lisa Carter", "003", department);
        session.persist(department);
        session.persist(employee1);
        session.persist(employee2);
        session.persist(employee3);
        transaction.commit();
        transaction = session.beginTransaction();
    }
    
    @After
    public void tearDown() {
        if(transaction.isActive()) {
            transaction.rollback();
        }
    }

    @Test
    public void givenMultipleEmployees_WhenFindByEmployeeNumberIsExecutedWithResultClass_ThenCorrectEmployeeIsReturned() {
        Query<DeptEmployee> query = session.createNamedQuery("DeptEmployee_FindByEmployeeNumber", DeptEmployee.class);
        query.setParameter("employeeNo", "001");
        DeptEmployee result = query.getSingleResult();
        Assert.assertNotNull(result);
        Assert.assertEquals("John Wayne", result.getName());
    }
       
    @Test
    public void whenNamedNativeQueryIsCalledUsingCreateNamedQuery_ThenOk() {
        Query<DeptEmployee> query = session.createNamedQuery("DeptEmployee_FindByEmployeeName", DeptEmployee.class);
        query.setParameter("name", "John Wayne");
        DeptEmployee result = query.getSingleResult();
        Assert.assertNotNull(result);
        Assert.assertEquals("001", result.getEmployeeNumber());
    }
    
    @Test
    public void whenNamedNativeQueryIsCalledUsingGetNamedNativeQuery_ThenOk() {
        @SuppressWarnings("rawtypes")
        NativeQuery query = session.getNamedNativeQuery("DeptEmployee_FindByEmployeeName");
        query.setParameter("name", "John Wayne");
        DeptEmployee result = (DeptEmployee) query.getSingleResult();
        Assert.assertNotNull(result);
        Assert.assertEquals("001", result.getEmployeeNumber());
    }
    
    @Test
    public void whenNamedStoredProcedureIsCalled_ThenOk() {
        Query spQuery = session.createNamedQuery("DeptEmployee_UpdateEmployeeDesignation");
        spQuery.setParameter("employeeNumber", "002");
        spQuery.setParameter("newDesignation", "Supervisor");
        spQuery.executeUpdate();
        transaction.commit();
        transaction.begin();
        Query<DeptEmployee> query = session.createNamedQuery("DeptEmployee_FindByEmployeeNumber", DeptEmployee.class);
        query.setParameter("employeeNo", "002");
        DeptEmployee result = query.getSingleResult();
        Assert.assertNotNull(result);
        Assert.assertEquals("Supervisor", result.getDesignation());
    }
}
