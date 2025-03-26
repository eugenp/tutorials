package com.baeldung.jpa.queryparams;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * JPAQueryParamsTest class tests.
 *
 * @author gmlopez.mackinnon@gmail.com
 */
public class JPAQueryParamsUnitTest {

    private static EntityManager entityManager;

    @BeforeClass
    public static void setup() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa-h2-queryparams");
        entityManager = factory.createEntityManager();
    }

    @Test
    public void givenEmpNumber_whenUsingPositionalParameter_thenReturnExpectedEmployee() {
        TypedQuery<Employee> query = entityManager.createQuery("SELECT e FROM Employee e WHERE e.empNumber = ?1", Employee.class);
        String empNumber = "A123";
        Employee employee = query.setParameter(1, empNumber)
          .getSingleResult();
        Assert.assertNotNull("Employee not found", employee);
    }

    @Test
    public void givenEmpNumberList_whenUsingPositionalParameter_thenReturnExpectedEmployee() {
        TypedQuery<Employee> query = entityManager.createQuery("SELECT e FROM Employee e WHERE e.empNumber IN (?1)", Employee.class);
        List<String> empNumbers = Arrays.asList("A123", "A124");
        List<Employee> employees = query.setParameter(1, empNumbers)
          .getResultList();
        Assert.assertNotNull("Employees not found", employees);
        Assert.assertFalse("Employees not found", employees.isEmpty());
    }

    @Test
    public void givenEmpNumber_whenUsingNamedParameter_thenReturnExpectedEmployee() {
        TypedQuery<Employee> query = entityManager.createQuery("SELECT e FROM Employee e WHERE e.empNumber = :number", Employee.class);
        String empNumber = "A123";
        Employee employee = query.setParameter("number", empNumber)
          .getSingleResult();
        Assert.assertNotNull("Employee not found", employee);
    }

    @Test
    public void givenEmpNumberList_whenUsingNamedParameter_thenReturnExpectedEmployee() {
        TypedQuery<Employee> query = entityManager.createQuery("SELECT e FROM Employee e WHERE e.empNumber IN (:numbers)", Employee.class);
        List<String> empNumbers = Arrays.asList("A123", "A124");
        List<Employee> employees = query.setParameter("numbers", empNumbers)
          .getResultList();
        Assert.assertNotNull("Employees not found", employees);
        Assert.assertFalse("Employees not found", employees.isEmpty());
    }

    @Test
    public void givenEmpNameAndEmpAge_whenUsingTwoNamedParameters_thenReturnExpectedEmployees() {
        TypedQuery<Employee> query = entityManager.createQuery("SELECT e FROM Employee e WHERE e.name = :name AND e.age = :empAge", Employee.class);
        String empName = "John Doe";
        int empAge = 55;
        List<Employee> employees = query.setParameter("name", empName)
          .setParameter("empAge", empAge)
          .getResultList();
        Assert.assertNotNull("Employees not found!", employees);
        Assert.assertTrue("Employees not found!", !employees.isEmpty());
    }

    @Test
    public void givenEmpNumber_whenUsingCriteriaQuery_thenReturnExpectedEmployee() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Employee> cQuery = cb.createQuery(Employee.class);
        Root<Employee> c = cQuery.from(Employee.class);
        ParameterExpression<String> paramEmpNumber = cb.parameter(String.class);
        cQuery.select(c)
          .where(cb.equal(c.get(Employee_.empNumber), paramEmpNumber));

        TypedQuery<Employee> query = entityManager.createQuery(cQuery);
        String empNumber = "A123";
        query.setParameter(paramEmpNumber, empNumber);
        Employee employee = query.getSingleResult();
        Assert.assertNotNull("Employee not found!", employee);
    }

    @Test
    public void givenEmpNumber_whenUsingLiteral_thenReturnExpectedEmployee() {
        String empNumber = "A123";
        TypedQuery<Employee> query = entityManager.createQuery("SELECT e FROM Employee e WHERE e.empNumber = '" + empNumber + "'", Employee.class);
        Employee employee = query.getSingleResult();
        Assert.assertNotNull("Employee not found!", employee);
    }

}
