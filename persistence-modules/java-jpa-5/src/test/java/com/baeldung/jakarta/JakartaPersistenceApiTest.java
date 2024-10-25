package com.baeldung.jakarta;

import static com.baeldung.jakarta.Employee_.QUERY_EMPLOYEE_BY_DEPARTMENT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.TypedQueryReference;
import jakarta.persistence.metamodel.EntityType;

import javax.naming.InitialContext;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class JakartaPersistenceApiTest {

    private static EntityManagerFactory emf;

    private static EntityManager em;

    @Before
    public void setUp() throws Exception {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:jakartaPersistenceApiTest");
        dataSource.setUsername("sa");

        InitialContext ctx = new InitialContext();
        ctx.bind("java:comp/env/jdbc/EmployeeData", dataSource);

        emf = createEntityManagerFactory();
        em = emf.createEntityManager();

        emf.getSchemaManager()
            .create(true);

        EntityType<?> employeeEntity = emf.getMetamodel()
            .entity(Employee.class);
        assertNotNull(employeeEntity, "Employee entity should be part of the metamodel");

        emf.runInTransaction(em -> {
            em.persist(new Employee(1L, "John Smith", "Finance"));
            em.persist(new Employee(2L, "Tom Riddle", "Art"));
            em.persist(new Employee(3L, "Harry Potter", "Magic"));
            em.persist(new Employee(4L, "Jade Gringer", "Education"));
            em.persist(new Employee(5L, "Cole Powell", "Science"));
            em.persist(new Employee(6L, "Jane Guducu", "Engineering"));
            em.persist(new Employee(7L, "Tony Blair", "Engineering"));
        });
    }

    @After
    public void teardown() {
        emf.runInTransaction(em -> em.runWithConnection(connection -> {
            try (var stmt = ((Connection) connection).createStatement()) {
                stmt.execute("delete from employee");
            } catch (Exception e) {
                Assertions.fail("JDBC operation failed");
            }
        }));

        emf.close();
    }

    @Test
    public void whenUsingPersistenceConfiguration_thenEntityManagerFactoryIsCreated() {
        assertNotNull(emf);
    }

    @Test
    public void whenRunInTransactionAndCallInTransaction_thenTheEntityManagerWorksWithTransaction() {
        emf.runInTransaction(em -> em.runWithConnection(connection -> {
            try (var stmt = ((Connection) connection).createStatement()) {
                stmt.execute("INSERT INTO employee (id, fullName, department) VALUES (8, 'Jane Smith', 'HR')");
            } catch (Exception e) {
                Assertions.fail("JDBC operation failed");
            }
        }));

        var employee = emf.callInTransaction(em -> em.find(Employee.class, 8L));
        assertNotNull(employee);
        assertEquals("Jane Smith", employee.getFullName());
    }

    @Test
    public void whenUsingEnhancedJpql_thenNewFeaturesWorks() {
        Employee employee = emf.callInTransaction(em -> em.createQuery("from Employee where fullName = 'Tony Blair'", Employee.class)
            .getSingleResult());

        assertNotNull(employee);
    }

    @Test
    public void givenNamedQuery_whenQueriedByDepartment_thenReturnCorrectEmployee() {

        Map<String, TypedQueryReference<Employee>> namedQueries = emf.getNamedQueries(Employee.class);

        List<Employee> employees = em.createQuery(namedQueries.get(QUERY_EMPLOYEE_BY_DEPARTMENT))
            .setParameter("department", "Science")
            .getResultList();

        assertEquals(1, employees.size());
    }

    @Test
    public void whenFindEmployeeWithEntityGraph_thenReturnEmployeeWithDepartment() {
        var employeeGraph = emf.callInTransaction(em -> em.createEntityGraph(Employee.class));
        employeeGraph.addAttributeNode(Employee_.department);

        var employee = emf.callInTransaction(em -> em.find(employeeGraph, 7L));
        assertNotNull(employee);
        assertEquals("Engineering", employee.getDepartment());
    }

    @Test
    public void whenCastFullNameToInteger_thenReturnCorrectResult() {
        emf.runInTransaction(em -> em.persist(new Employee(11L, "123456", "Art")));

        TypedQuery<Integer> query = em.createQuery("select cast(e.fullName as integer) from Employee e where e.id = 11", Integer.class);
        Integer result = query.getSingleResult();

        assertEquals(123456, result);
    }

    @Test
    public void whenLeftAndRightFullName_thenReturnSubstring() {
        TypedQuery<String> query = em.createQuery("select left(e.fullName, 3) from Employee e where e.id = 2", String.class);
        String result = query.getSingleResult();

        assertEquals("Tom", result);

        query = em.createQuery("select right(e.fullName, 6) from Employee e where e.id = 2", String.class);
        result = query.getSingleResult();

        assertEquals("Riddle", result);
    }

    @Test
    public void whenReplaceFullName_thenReturnModifiedName() {

        TypedQuery<String> query = em.createQuery("select replace(e.fullName, 'Jade', 'Jane') from Employee e where e.id = 4", String.class);
        String result = query.getSingleResult();

        assertEquals("Jane Gringer", result);
    }

    @Test
    public void whenUseIdFunction_thenReturnEmployeeId() {
        TypedQuery<Long> query = em.createQuery("select id(e) from Employee e where e.fullName = 'John Smith'", Long.class);
        Long result = query.getSingleResult();

        assertEquals(1L, result);
    }

    @Test
    public void whenSortEmployeesByFullName_thenReturnSortedList() {
        emf.runInTransaction(em -> {
            em.persist(new Employee(21L, "alice", "HR"));
            em.persist(new Employee(22L, "Bob", "Engineering"));
            em.persist(new Employee(23L, null, "Finance"));
            em.persist(new Employee(24L, "charlie", "HR"));
        });

        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e ORDER BY lower(e.fullName) ASC NULLS FIRST, e.id DESC", Employee.class);

        List<Employee> sortedEmployees = query.getResultList();

        // Assertions
        assertEquals(11, sortedEmployees.size());
        assertNull(sortedEmployees.get(0)
            .getFullName());
        assertEquals("alice", sortedEmployees.get(1)
            .getFullName());
        assertEquals("Bob", sortedEmployees.get(2)
            .getFullName());
        assertEquals("charlie", sortedEmployees.get(3)
            .getFullName());
    }

    private EntityManagerFactory createEntityManagerFactory() {
        return new PersistenceConfiguration("EmployeeData").jtaDataSource("java:comp/env/jdbc/EmployeeData")
            .managedClass(Employee.class)
            .property(PersistenceConfiguration.LOCK_TIMEOUT, 5000)
            .createEntityManagerFactory();
    }

}