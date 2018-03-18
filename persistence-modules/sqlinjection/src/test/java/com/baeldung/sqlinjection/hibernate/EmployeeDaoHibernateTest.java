package com.baeldung.sqlinjection.hibernate;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.sqlinjection.model.Employee;

public class EmployeeDaoHibernateTest {

    private SessionFactory factory;

    @Before
    public final void before() {
        factory = new Configuration().configure("employee.cfg.xml")
            .buildSessionFactory();

        initializeData();
    }

    @After
    public final void after() {
        factory.close();
    }

    private void initializeData() {
        try (Session session = factory.openSession()) {
            Employee emp1 = new Employee(1L, "Trudy");
            Employee emp2 = new Employee(2L, "Bob");

            session.beginTransaction();
            session.save(emp1);
            session.save(emp2);
            session.getTransaction()
                .commit();
        }
    }

    @Test
    public void givenEmployees_whenSearchWithGoodQuery_thenFindOk() {

        EmployeeDaoHibernate empDaoHibernate = new EmployeeDaoHibernate(factory);

        // search by name
        List<Employee> result = empDaoHibernate.searchEmployeesSecure1("Tru%");

        assertTrue(result.size() == 1);
        assertTrue(result.get(0)
            .getName()
            .equalsIgnoreCase("Trudy"));
    }

    @Test
    public void givenEmployees_whenSearchWithBadQuery_thenNoHarm() {

        EmployeeDaoHibernate empDaoHibernate = new EmployeeDaoHibernate(factory);

        // search by name with injected malice to return all records instead
        List<Employee> result = empDaoHibernate.searchEmployeesSecure1("Trudy' or 1=1 or '1'='1");

        assertTrue(result.size() == 0);
    }

    @Test
    public void givenEmployees_whenSearchWithBadQueryInsecure_thenHarm() {

        EmployeeDaoHibernate empDaoHibernate = new EmployeeDaoHibernate(factory);

        // search by name with injected malice to return all records instead
        List<Employee> result = empDaoHibernate.searchEmployeesInsecure("Trudy' or 1=1 or '1'='1");

        // should have been only 1, but all returned due to malicious injection
        assertTrue(result.size() > 1);
    }
}
