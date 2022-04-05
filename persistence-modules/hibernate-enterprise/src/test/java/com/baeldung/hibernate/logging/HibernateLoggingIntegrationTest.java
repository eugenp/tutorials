package com.baeldung.hibernate.logging;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.hibernate.logging.Employee;

public class HibernateLoggingIntegrationTest {

    private SessionFactory sessionFactory;

    @Before
    public void setUp() throws IOException {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate-logging.cfg.xml")
            .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata()
                .buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(new Employee("John Smith", "001"));
            session.getTransaction()
                .commit();
            session.close();
        } catch (Exception e) {
            fail(e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
    
    @Test
    public void whenAllEmployeesAreSelected_ThenSuccess() {
        Query<Employee> query = sessionFactory.openSession().createQuery("from com.baeldung.hibernate.logging.Employee", Employee.class);
        List<Employee> deptEmployees = query.list();
        Employee deptEmployee = deptEmployees.get(0);
        assertEquals("John Smith", deptEmployee.getName());
    }
}
