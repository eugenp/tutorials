package com.baeldung.sqlinjection.jpa;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.sqlinjection.model.Employee;

public class EmployeeDaoJpaTest {

    private EntityManagerFactory factory;
    private EntityManager manager;

    @Before
    public final void before() {
        factory = Persistence.createEntityManagerFactory("jpa-db");
        manager = factory.createEntityManager();

        initializeData();
    }

    @After
    public final void after() {
        manager.close();
        factory.close();
    }

    private void initializeData() {

        Employee emp1 = new Employee(1L, "Trudy");
        Employee emp2 = new Employee(2L, "Bob");
        manager.getTransaction()
            .begin();
        manager.persist(emp1);
        manager.persist(emp2);
        manager.getTransaction()
            .commit();
    }

    @Test
    public void givenEmployees_whenSearchWithGoodQuery_thenFindOk() {

        EmployeeDaoJpa empDao = new EmployeeDaoJpa();

        // search by name
        List<Employee> result = empDao.searchEmployeesSecure1("Tru%");

        assertTrue(result.size() == 1);
        assertTrue(result.get(0)
            .getName()
            .equalsIgnoreCase("Trudy"));
    }

    @Test
    public void givenEmployees_whenSearchWithBadQuery_thenNoHarm() {

        EmployeeDaoJpa empDao = new EmployeeDaoJpa();

        // search by name with injected malice to return all records instead
        List<Employee> result = empDao.searchEmployeesSecure1("Trudy' or 1=1 or '1'='1");

        assertTrue(result.size() == 0);
    }

    @Test
    public void givenEmployees_whenSearchWithBadQueryInsecure_thenHarm() {

        EmployeeDaoJpa empDao = new EmployeeDaoJpa();

        // search by name with injected malice to return all records instead
        List<Employee> result = empDao.searchEmployeesInsecure("Trudy' or 1=1 or '1'='1");

        // should have been only 1, but all returned due to malicious injection
        assertTrue(result.size() > 1);
    }
}
