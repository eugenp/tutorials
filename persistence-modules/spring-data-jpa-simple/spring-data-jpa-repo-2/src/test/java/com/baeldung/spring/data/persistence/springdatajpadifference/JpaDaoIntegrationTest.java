package com.baeldung.spring.data.persistence.springdatajpadifference;

import static com.baeldung.spring.data.persistence.springdatajpadifference.TestUtils.employee;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import org.junit.Before;
import org.junit.Test;

import com.baeldung.spring.data.persistence.springdatajpadifference.model.Employee;
import com.baeldung.spring.data.persistence.springdatajpadifference.model.Employee_;

public class JpaDaoIntegrationTest {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu-test");
    private final EntityManager entityManager = emf.createEntityManager();

    @Before
    public void setup() {
        deleteAllEmployees();
    }

    @Test
    public void givenPersistedEmployee_whenFindById_thenEmployeeIsFound() {
        Employee employee = employee("John", "Doe");
        save(employee);

        assertEquals(employee, entityManager.find(Employee.class, employee.getId()));
    }

    @Test
    public void givenPersistedEmployee_whenFindByIdCriteriaQuery_thenEmployeeIsFound() {
        Employee employee = employee("John", "Doe");
        save(employee);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);
        criteriaQuery.select(root);

        criteriaQuery.where(criteriaBuilder.equal(root.get(Employee_.ID), employee.getId()));

        assertEquals(employee, entityManager.createQuery(criteriaQuery)
          .getSingleResult());
    }

    @Test
    public void givenPersistedEmployee_whenFindByIdJpql_thenEmployeeIsFound() {
        Employee employee = employee("John", "Doe");
        save(employee);

        Query jpqlQuery = entityManager.createQuery("SELECT e from Employee e WHERE e.id=:id");
        jpqlQuery.setParameter("id", employee.getId());

        assertEquals(employee, jpqlQuery.getSingleResult());
    }

    @Test
    public void givenPersistedEmployee_whenFindByIdNamedQuery_thenEmployeeIsFound() {
        Employee employee = employee("John", "Doe");
        save(employee);

        Query query = entityManager.createNamedQuery("Employee.findById");

        query.setParameter(Employee_.ID, employee.getId());

        assertEquals(employee, query.getSingleResult());
    }

    @Test
    public void givenPersistedEmployee_whenFindWithPaginationAndSort_thenEmployeesAreFound() {
        Employee john = employee("John", "Doe");
        Employee bob = employee("Bob", "Smith");
        Employee frank = employee("Frank", "Brown");
        Employee james = employee("James", "Smith");
        save(john);
        save(bob);
        save(frank);
        save(james);

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> root = criteriaQuery.from(Employee.class);
        criteriaQuery.select(root);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Employee_.FIRST_NAME)));

        TypedQuery<Employee> query = entityManager.createQuery(criteriaQuery);

        query.setFirstResult(0);
        query.setMaxResults(3);

        List<Employee> employeeList = query.getResultList();

        assertEquals(Arrays.asList(bob, frank, james), employeeList);
    }

    @Test
    public void givenPersistedEmployee_whenUpdateEmployeeEmail_thenEmployeeHasUpdatedEmail() {
        Employee employee = employee("John", "Doe");
        save(employee);

        Employee employeeToUpdate = entityManager.find(Employee.class, employee.getId());

        String updatedEmail = "email@gmail.com";

        employeeToUpdate.setEmail(updatedEmail);

        update(employeeToUpdate);

        assertEquals(updatedEmail, entityManager.find(Employee.class, employee.getId())
          .getEmail());
    }

    @Test
    public void givenPersistedEmployee_whenUpdateEmployeeEmailWithCriteria_thenEmployeeHasUpdatedEmail() {
        Employee employee = employee("John", "Doe");
        save(employee);

        String updatedEmail = "email@gmail.com";

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Employee> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(Employee.class);
        Root<Employee> root = criteriaUpdate.from(Employee.class);

        criteriaUpdate.set(Employee_.EMAIL, updatedEmail);
        criteriaUpdate.where(criteriaBuilder.equal(root.get(Employee_.ID), employee.getId()));

        assertEquals(1, update(criteriaUpdate));

        assertEquals(updatedEmail, entityManager.find(Employee.class, employee.getId())
          .getEmail());
    }

    @Test
    public void givenPersistedEmployee_whenRemoveEmployee_thenNoEmployeeIsFound() {
        Employee employee = employee("John", "Doe");
        save(employee);

        delete(employee.getId());

        assertNull(entityManager.find(Employee.class, employee.getId()));
    }

    private void deleteAllEmployees() {
        entityManager.getTransaction()
          .begin();
        entityManager.createNativeQuery("DELETE from Employee")
          .executeUpdate();
        entityManager.getTransaction()
          .commit();
    }

    public void save(Employee entity) {
        entityManager.getTransaction()
          .begin();
        entityManager.persist(entity);
        entityManager.getTransaction()
          .commit();
    }

    public void update(Employee entity) {
        entityManager.getTransaction()
          .begin();
        entityManager.merge(entity);
        entityManager.getTransaction()
          .commit();
    }

    public void delete(Long employee) {
        entityManager.getTransaction()
          .begin();
        entityManager.remove(entityManager.find(Employee.class, employee));
        entityManager.getTransaction()
          .commit();
    }

    public int update(CriteriaUpdate<Employee> criteriaUpdate) {
        entityManager.getTransaction()
          .begin();
        int result = entityManager.createQuery(criteriaUpdate)
          .executeUpdate();
        entityManager.getTransaction()
          .commit();
        entityManager.clear();

        return result;
    }
}
