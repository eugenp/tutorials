package com.baeldung.hibernate.manytomany;

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
import com.baeldung.manytomany.PersistenceConfig;
import com.baeldung.manytomany.model.Employee;
import com.baeldung.manytomany.model.Project;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class HibernateManyToManyAnnotationJavaConfigMainIntegrationTest {

    @Autowired
    private SessionFactory sessionFactory;

    private Session session;

    @Before
    public final void before() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @After
    public final void after() {
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public final void whenEntitiesAreCreated_thenNoExceptions() {
        Set<Project> projects = new HashSet<Project>();
        projects.add(new Project("IT Project"));
        projects.add(new Project("Networking Project"));
        session.persist(new Employee("Peter", "Oven", projects));
        session.persist(new Employee("Allan", "Norman", projects));
    }

}
