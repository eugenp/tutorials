package com.baeldung.hibernate.cte;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommonTableExpressionsUnitTest {

    private static SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    static void createSession() {
        sessionFactory = new Configuration()
            .addAnnotatedClass(Employee.class)
            .configure("cte/hibernate.cte.cfg.xml")
            .buildSessionFactory();
    }

    @BeforeEach
    void before() {
        session = sessionFactory.openSession();
    }

    @AfterEach
    void after() {
        session.close();
    }

    @Test
    void whenQueryingEngineers_thenCorrectRowsAreReturned() {
        List<Employee> employees = session.createQuery("""
            WITH engineers AS (
                SELECT e.id AS id
                FROM Employee e
                WHERE e.department = 'Engineering'
            )
            SELECT e
            FROM Employee e
            WHERE e.id IN (SELECT id FROM engineers)
            AND e.salary > 100000
            ORDER BY e.name ASC
        """, Employee.class).getResultList();

        List<String> names = employees.stream()
            .map(Employee::getName)
            .toList();

        assertEquals(List.of("Ben Rodgers", "David Kim", "Ella Novak", "Grace Lin"), names);
    }

    @Test
    void whenQueryingEmployeesPerDepartment_thenCorrectRowsAreReturned() {
        List<Employee> employees = session.createQuery("""
            WITH dept_avg AS (
                SELECT e.department AS dept, avg(e.salary) AS avgSalary
                FROM Employee e
                GROUP BY e.department
            )
            SELECT e
            FROM Employee e
            JOIN dept_avg d ON e.department = d.dept
            WHERE e.salary > d.avgSalary
            ORDER BY e.department, e.salary DESC
        """, Employee.class).getResultList();

        List<String> names = employees.stream()
            .map(Employee::getName)
            .toList();

        assertEquals(List.of("Ben Rodgers", "David Kim", "Ella Novak", "Carla Nunes", "Frank Osei"), names);
    }

    @Test
    void whenQueryingSubordinates_thenCorrectRowsAreReturned() {
        List<Employee> employees = session.createQuery("""
            WITH subordinates as (
                SELECT e.id AS id 
                FROM Employee e 
                WHERE e.manager.id = 2
            
                UNION
            
                SELECT e2.id AS id 
                FROM Employee e2 
                JOIN subordinates s ON e2.manager.id = s.id
            )
            SELECT e
            FROM Employee e
            WHERE e.id IN (SELECT s.id FROM subordinates s)
            ORDER BY e.department, e.name
            """, Employee.class).getResultList();

        List<String> names = employees.stream()
            .map(Employee::getName)
            .toList();

        assertEquals(List.of("David Kim", "Ella Novak", "Grace Lin", "Hugo Alvarez", "Ivy Chen", "Liam Foster"), names);
    }
}
