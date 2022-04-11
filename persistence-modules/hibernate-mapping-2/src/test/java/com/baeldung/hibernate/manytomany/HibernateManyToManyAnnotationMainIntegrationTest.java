package com.baeldung.hibernate.manytomany;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

import com.baeldung.manytomany.model.Employee;
import com.baeldung.manytomany.model.Project;
import com.baeldung.manytomany.util.HibernateUtil;

/**
 * Configured in: manytomany.cfg.xml
 */
public class HibernateManyToManyAnnotationMainIntegrationTest {
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
    public void givenSession_whenRead_thenReturnsMtoMdata() {
		prepareData();
       	@SuppressWarnings("unchecked")
		List<Employee> employeeList = session.createQuery("FROM Employee").list();
        @SuppressWarnings("unchecked")
		List<Project> projectList = session.createQuery("FROM Project").list();
        assertNotNull(employeeList);
        assertNotNull(projectList);
        assertEquals(2, employeeList.size());
        assertEquals(2, projectList.size());
        
        for(Employee employee : employeeList) {
            assertNotNull(employee.getProjects());
            assertEquals(2, employee.getProjects().size());
        }
        for(Project project : projectList) {
            assertNotNull(project.getEmployees());
            assertEquals(2, project.getEmployees().size());
        }
    }

	private void prepareData() {
		String[] employeeData = { "Peter Oven", "Allan Norman" };
		String[] projectData = { "IT Project", "Networking Project" };
		Set<Project> projects = new HashSet<Project>();

		for (String proj : projectData) {
			projects.add(new Project(proj));
		}

		for (String emp : employeeData) {
			Employee employee = new Employee(emp.split(" ")[0], emp.split(" ")[1]);
			employee.setProjects(projects);
			
			for (Project proj : projects) {
				proj.getEmployees().add(employee);
			}
			
			session.persist(employee);
		}
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
