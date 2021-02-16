package com.baeldung.hibernate.lazyCollection;

import com.baeldung.hibernate.lazyCollection.model.Branch;
import com.baeldung.hibernate.lazyCollection.model.Employee;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.service.ServiceRegistry;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.annotation.PostConstruct;

public class LazyCollectionTest {

    private static SessionFactory sessionFactory;

    private Session session;

    Branch branch;

    @BeforeClass
    public static void beforeTests() {
        Configuration configuration = new Configuration().addAnnotatedClass(Branch.class).addAnnotatedClass(Employee.class)
                .setProperty("hibernate.dialect", H2Dialect.class.getName())
                .setProperty("hibernate.connection.driver_class", org.h2.Driver.class.getName())
                .setProperty("hibernate.connection.url", "jdbc:h2:mem:test")
                .setProperty("hibernate.connection.username", "sa").setProperty("hibernate.connection.password", "")
                .setProperty("hibernate.hbm2ddl.auto", "update");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);

    }

    @Before
    public void setUp() {
        session = sessionFactory.openSession();
        session.beginTransaction();

        branch = new Branch("Main Branch");

        session.save(branch);

        Employee mainEmployee1 = new Employee("main employee 1", 1L,  branch, null, null);
        Employee mainEmployee2 = new Employee("main employee 2", 2L, branch, null, null);
        Employee mainEmployee3 = new Employee("main employee 3", 3L, branch, null, null);

        session.save(mainEmployee1);
        session.save(mainEmployee2);
        session.save(mainEmployee3);

        Employee subEmployee1 = new Employee("sub employee 1", 1L, null, branch, null);
        Employee subEmployee2 = new Employee("sub employee 2", 2L, null, branch, null);
        Employee subEmployee3 = new Employee("sub employee 3", 3L, null, branch, null);

        session.save(subEmployee1);
        session.save(subEmployee2);
        session.save(subEmployee3);

        Employee additionalEmployee1 = new Employee("additional employee 1", 1L, null, null, branch);
        Employee additionalEmployee2 = new Employee("additional employee 2", 2L, null, null, branch);
        Employee additionalEmployee3 = new Employee("additional employee 3", 3L, null, null, branch);

        session.save(additionalEmployee1);
        session.save(additionalEmployee2);
        session.save(additionalEmployee3);

        session.flush();
        session.refresh(branch);
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testLazyFetching() {
        Assert.assertFalse(Hibernate.isInitialized(branch.getMainEmployees()));
    }

    @Test
    public void testEagerFetching() {
        Assert.assertTrue(Hibernate.isInitialized(branch.getSubEmployees()));
    }

    @Test
    public void testExtraFetching() {
        Assert.assertFalse(Hibernate.isInitialized(branch.getAdditionalEmployees()));
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
