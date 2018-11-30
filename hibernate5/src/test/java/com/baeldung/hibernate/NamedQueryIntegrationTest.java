package com.baeldung.hibernate;

import com.baeldung.hibernate.entities.Department;
import com.baeldung.hibernate.entities.DeptEmployee;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;

import javax.transaction.Transactional;

public class NamedQueryIntegrationTest {
    private Session session;

    private Transaction transaction;

    @Before
    public void setUp() throws IOException {
        session = HibernateUtil.getSessionFactory().openSession();
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

    @Test
    public void givenMultipleEmployees_WhenFindByEmployeeNumberIsExecutedWithResultClass_ThenCorrectEmployeeIsReturned() {
        Query<DeptEmployee> query = session.createNamedQuery("DeptEmployee_findByEmployeeNumber", DeptEmployee.class);
        query.setParameter("employeeNo", "001");
        DeptEmployee result = query.getSingleResult();
        Assert.assertNotNull(result);
        Assert.assertEquals("John Wayne", result.getName());
    }
    
    @Test
    public void whenUpdateEmployeeDesignationIsExecuted_ThenEmployeeDesignationIsUpdated() {
        Query updateQuery = session.createNamedQuery("DeptEmployee_UpdateEmployeeDesignation");
        updateQuery.setParameter("employeeNo", "001");
        updateQuery.setParameter("newDesignation", "Supervisor");
        updateQuery.executeUpdate();

        Query<DeptEmployee> readQuery = session.createNamedQuery("DeptEmployee_FindByEmployeeNumber", DeptEmployee.class);
        readQuery.setParameter("employeeNo", "001");
        DeptEmployee result = readQuery.getSingleResult();
        Assert.assertNotNull(result);
        Assert.assertEquals("Supervisor", result.getDesignation()); 
        transaction.commit();
    }
}
