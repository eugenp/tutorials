package com.baeldung.hibernate.lazycollection;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.service.ServiceRegistry;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.hibernate.lazycollection.model.Branch;
import com.baeldung.hibernate.lazycollection.model.Employee;

public class LazyCollectionIntegrationTest {

    private static SessionFactory sessionFactory;

    private Session session;

    Branch branch;

    @BeforeClass
    public static void beforeTests() {
        Configuration configuration = new Configuration().addAnnotatedClass(Branch.class)
          .addAnnotatedClass(Employee.class).setProperty("hibernate.dialect", H2Dialect.class.getName())
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

        Employee mainEmployee1 = new Employee("main employee 1", branch, null, null);
        Employee mainEmployee2 = new Employee("main employee 2", branch, null, null);
        Employee mainEmployee3 = new Employee("main employee 3", branch, null, null);

        session.save(mainEmployee1);
        session.save(mainEmployee2);
        session.save(mainEmployee3);

        Employee subEmployee1 = new Employee("sub employee 1", null, branch, null);
        Employee subEmployee2 = new Employee("sub employee 2", null, branch, null);
        Employee subEmployee3 = new Employee("sub employee 3", null, branch, null);

        session.save(subEmployee1);
        session.save(subEmployee2);
        session.save(subEmployee3);

        Employee additionalEmployee1 = new Employee("additional employee 1", null, null, branch);
        Employee additionalEmployee2 = new Employee("additional employee 2", null, null, branch);
        Employee additionalEmployee3 = new Employee("additional employee 3", null, null, branch);

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

    @AfterClass
    public static void afterTests() {
        sessionFactory.close();
    }
}
