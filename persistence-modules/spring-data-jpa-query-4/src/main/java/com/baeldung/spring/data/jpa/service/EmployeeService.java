package com.baeldung.spring.data.jpa.service;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;

import com.baeldung.spring.data.jpa.entity.Department;
import com.baeldung.spring.data.jpa.entity.Employee;

@Service
public class EmployeeService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Employee> getAllEmployeesUsingJPQL() {
        String jpqlQuery = "SELECT e FROM Employee e";
        Query query = entityManager.createQuery(jpqlQuery, Employee.class);
        return query.getResultList();
    }

    public List<Employee> getAllEmployeesByDepartmentUsingJPQL() {
        String jpqlQuery = "SELECT e FROM Employee e WHERE e.department = 'Engineering' ORDER BY e.lastName ASC";
        Query query = entityManager.createQuery(jpqlQuery, Employee.class);

        return query.getResultList();
    }

    public List<Employee> getAllEmployeesUsingNamedQuery() {
        String namedQuery = "findAllEmployees";
        Query query = entityManager.createNamedQuery(namedQuery, Employee.class);

        return query.getResultList();
    }

    public List<Employee> getAllEmployeesByDepartmentUsingNamedQuery(String department) {
        String namedQuery = "findEmployeesByDepartment";
        Query query = entityManager.createNamedQuery(namedQuery, Employee.class);
        query.setParameter("department", department);

        return query.getResultList();
    }

    public List<Employee> getAllEmployeesUsingCriteriaAPI() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);

        criteriaQuery.select(employeeRoot);
        Query query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }

    public List<Employee> getAllEmployeesByDepartmentUsingCriteriaAPI(String department) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);
        Predicate departmentPredicate = criteriaBuilder.equal(employeeRoot.get("department"), department);
        Path<Object> sortByPath = employeeRoot.get("lastName");
        criteriaQuery.orderBy(criteriaBuilder.asc(sortByPath));
        criteriaQuery.select(employeeRoot)
            .where(departmentPredicate);
        Query query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }

    public List<Employee> getAllEmployeesByDepartmentUsingOneToManyAnnotations(String departmentName) {
        try {
            // Retrieve the department by its name
            Department department = entityManager.createQuery("SELECT d FROM Department d WHERE d.name = :name", Department.class)
                .setParameter("name", departmentName)
                .getSingleResult();

            // Return the list of employees associated with the department
            return department.getEmployees();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }
}
