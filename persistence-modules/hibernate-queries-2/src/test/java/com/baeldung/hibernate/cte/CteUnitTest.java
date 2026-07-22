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

public class CteUnitTest {

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
            with engineers as (
                select e.id as id
                from Employee e
                where e.department = 'Engineering'
            )
            select e
            from Employee e
            where e.id in (select id from engineers)
            and e.salary > 100000
            order by e.name asc
        """, Employee.class).getResultList();

        List<String> names = employees.stream().map(Employee::getName).toList();

        assertEquals(List.of("Ben Rodgers", "David Kim", "Ella Novak", "Grace Lin"), names);
    }

    @Test
    void whenQueryingEmployeesPerDepartment_thenCorrectRowsAreReturned() {
        List<Employee> employees = session.createQuery("""
            with dept_avg as (
                select e.department as dept, avg(e.salary) as avgSalary
                from Employee e
                group by e.department
            )
            select e
            from Employee e
            join dept_avg d on e.department = d.dept
            where e.salary > d.avgSalary
            order by e.department, e.salary desc
        """, Employee.class).getResultList();

        List<String> names = employees.stream().map(Employee::getName).toList();

        assertEquals(List.of("Ben Rodgers", "David Kim", "Ella Novak", "Carla Nunes", "Frank Osei"), names);
    }

    @Test
    void whenQueryingSubordinates_thenCorrectRowsAreReturned() {
        List<Employee> employees = session.createQuery("""
            with subordinates as (
                select e.id as id from Employee e where e.manager.id = 2
            
                union
            
                select e2.id as id from Employee e2 join subordinates s on e2.manager.id = s.id
            )
            select e
            from Employee e
            where e.id in (select s.id from subordinates s)
            order by e.department, e.name
            """, Employee.class).getResultList();

        List<String> names = employees.stream().map(Employee::getName).toList();

        assertEquals(List.of("David Kim", "Ella Novak", "Grace Lin", "Hugo Alvarez", "Ivy Chen", "Liam Foster"), names);
    }
}
