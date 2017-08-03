package com.baeldung.hibernate.manyToMany;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static junit.framework.TestCase.assertEquals;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.AssertTrue;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.hibernate.manyToMany.config.HibernateUtil;
import com.baeldung.hibernate.manyToMany.model.Employee;
import com.baeldung.hibernate.manyToMany.model.Project;


public class HibernateOneToManyAnnotationXMLConfigMainIntegrationTest {
    private static SessionFactory sessionFactory;

    private Session session;


    @BeforeClass
    public static void beforeTests() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Before
    public void setUp() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    
    @Test
    public void givenSession_checkIfDatabaseIsPopulated() {
        Employee employee1 = new Employee("Peter", "Oven");
        Set<Project> projects = new HashSet<Project>();
        projects = employee1.getProjects();
        int noProjects = projects.size();
        assertEquals(0,noProjects);
        Project project1 = new Project("IT Project");
        assertNotNull(project1);
        projects.add(project1);
        Project project2 = new Project("Networking Project");
        assertNotNull(project2);
        projects.add(project2);
        employee1.setProjects(projects);
        assertNotNull(employee1);
        Employee employee2 = new Employee("Allan", "Norman");
        employee2.setProjects(projects);
        assertNotNull(employee2);
       
        session.persist(employee1);
        session.persist(employee2);
        session.getTransaction().commit();
        session.close();

        session = sessionFactory.openSession();
        session.beginTransaction();
        project1 = (Project) session.get(Project.class, new Long(1));
        assertNotNull(project1);
        project2 = (Project) session.get(Project.class, new Long(1));
        assertNotNull(project2);
        employee1 = (Employee) session.get(Employee.class, new Long(1));
        assertNotNull(employee1);
        employee2 = (Employee) session.get(Employee.class, new Long(1));
        assertNotNull(employee2);
    }


    @After
    public void tearDown() {
        session.getTransaction().commit();
        session.close();
    }

    @AfterClass
    public static void afterTests() {
        sessionFactory.close();
    }

}
