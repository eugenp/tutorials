package com.baeldung.hibernate.manyToMany;


import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.hibernate.manyToMany.model.Employee;
import com.baeldung.hibernate.manyToMany.model.Project;
import com.baeldung.hibernate.manyToMany.spring.PersistanceConfig;
import com.baeldung.hibernate.manyToMany.usage.IEmployeeService;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistanceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class HibernateManyToManyAnnotationJavaConfigMainIntegrationTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private IEmployeeService employeeService;

    private Session session;


    @Before
    public final void before() {
        session = sessionFactory.openSession();
    }

    @After
    public final void after() {
        session.close();
    }

    @Test
    public final void whenEntitiesAreCreated_thenNoExceptions() {
       Set<Project> projects = new HashSet<Project>();
       projects.add(new Project("IT Project"));
       projects.add(new Project("Networking Project"));
       employeeService.create(new Employee("Peter", "Oven", projects));
       employeeService.create(new Employee("Allan", "Norman", projects));
    }

}
