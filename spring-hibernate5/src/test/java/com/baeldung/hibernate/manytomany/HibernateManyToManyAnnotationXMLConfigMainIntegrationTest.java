package com.baeldung.hibernate.manytomany;

import static org.junit.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;





import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.hibernate.manytomany.util.HibernateUtil;
import com.baeldung.hibernate.manytomany.model.Employee;
import com.baeldung.hibernate.manytomany.model.Project;



public class HibernateManyToManyAnnotationXMLConfigMainIntegrationTest {
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
        @SuppressWarnings("unchecked")
        List<Project> projectList = session.createQuery("FROM Project").list();
        assertNotNull(projectList);
        @SuppressWarnings("unchecked")
        List<Employee> employeeList = session.createQuery("FROM Employee").list();
        assertNotNull(employeeList);
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
