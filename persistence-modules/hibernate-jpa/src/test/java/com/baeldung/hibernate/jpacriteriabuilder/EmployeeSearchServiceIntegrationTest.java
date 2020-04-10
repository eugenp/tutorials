package com.baeldung.hibernate.jpacriteriabuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.hibernate.HibernateUtil;
import com.baeldung.hibernate.entities.Department;
import com.baeldung.hibernate.entities.DeptEmployee;
import com.baeldung.hibernate.jpacriteriabuilder.service.EmployeeSearchService;
import com.baeldung.hibernate.jpacriteriabuilder.service.EmployeeSearchServiceImpl;

public class EmployeeSearchServiceIntegrationTest {

    private EntityManager entityManager;
    private EmployeeSearchService searchService;
    private Session session;

    @Before
    public final void setup() throws HibernateException, IOException {
        session = HibernateUtil.getSessionFactory()
            .openSession();
        entityManager = session.getEntityManagerFactory()
            .createEntityManager();
        searchService = new EmployeeSearchServiceImpl(entityManager);

        entityManager.getTransaction()
            .begin();
        Department department = new Department("Pre Sales");
        DeptEmployee employee = new DeptEmployee("John Smith", "001", "Manager", department);
        entityManager.persist(department);
        entityManager.persist(employee);
        employee = new DeptEmployee("Ian Evans", "002", "Associate", department);
        entityManager.persist(department);
        entityManager.persist(employee);
        department = new Department("Copporate Sales");
        employee = new DeptEmployee("Robert Carter", "003", "Manager", department);
        entityManager.persist(department);
        entityManager.persist(employee);
        employee = new DeptEmployee("John Carter", "004", "Senior Manager", department);
        entityManager.persist(employee);
        employee = new DeptEmployee("David Guetta", "009", "Associate", department);
        entityManager.persist(department);
        entityManager.persist(employee);
        department = new Department("Post Sales");
        employee = new DeptEmployee("Robert Jonas", "005", "Director", department);
        entityManager.persist(department);
        entityManager.persist(employee);
        employee = new DeptEmployee("John Ferros", "006", "Junior Associate", department);
        entityManager.persist(department);
        entityManager.persist(employee);
        department = new Department("Client Support");
        employee = new DeptEmployee("Robert Mcclements", "007", "Director", department);
        entityManager.persist(department);
        entityManager.persist(employee);
        employee = new DeptEmployee("Peter Parker", "008", "Manager", department);
        entityManager.persist(department);
        entityManager.persist(employee);

    }

    @After
    public final void teardown() {
        entityManager.getTransaction()
            .rollback();
        entityManager.close();
    }

    @Test
    public final void givenCriteriaQuery_whenSearchedUsingCriteriaBuilderWithListofAuthors_thenResultIsFilteredByAuthorNames() {
        List<String> titles = new ArrayList<String>() {
            {
                add("Manager");
                add("Senior Manager");
                add("Director");
            }
        };
        List<DeptEmployee> result = searchService.filterbyTitleUsingCriteriaBuilder(titles);
        assertEquals("Number of Employees does not match with expected.", 6, result.size());
        assertThat(result.stream()
            .map(DeptEmployee::getTitle)
            .distinct()
            .collect(Collectors.toList()), containsInAnyOrder(titles.toArray()));
    }

    @Test
    public final void givenCriteriaQuery_whenSearchedUsingExpressionWithListofAuthors_thenResultIsFilteredByAuthorNames() {
        List<String> titles = new ArrayList<String>() {
            {
                add("Manager");
                add("Senior Manager");
                add("Director");
            }
        };
        List<DeptEmployee> result = searchService.filterbyTitleUsingExpression(titles);
        assertEquals("Number of Employees does not match with expected.", 6, result.size());
        assertThat(result.stream()
            .map(DeptEmployee::getTitle)
            .distinct()
            .collect(Collectors.toList()), containsInAnyOrder(titles.toArray()));
    }

    @Test
    public final void givenCriteriaQuery_whenSearchedDepartmentLike_thenResultIsFilteredByDepartment() {
        List<DeptEmployee> result = searchService.searchByDepartmentQuery("Sales");
        assertEquals("Number of Employees does not match with expected.", 7, result.size());
    }
}
