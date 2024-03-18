package com.baeldung.hibernate.unknownentityexception;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.h2.Driver;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UnknownEntityExceptionUnitTest {

  private static SessionFactory sessionFactory;
  private Session session;

  @BeforeAll
  public static void beforeTests() {
    Configuration configuration = new Configuration().addAnnotatedClass(Customer.class)
        .setProperty("hibernate.dialect", H2Dialect.class.getName())
        .setProperty("hibernate.connection.driver_class", Driver.class.getName())
        .setProperty("hibernate.connection.url", "jdbc:h2:mem:customerdb")
        .setProperty("hibernate.connection.username", "sa")
        .setProperty("hibernate.connection.password", "")
        .setProperty("hibernate.hbm2ddl.auto", "update");
    ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
        .build();
    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
  }

  @BeforeEach
  public void setUp() {
    session = sessionFactory.openSession();
    session.beginTransaction();
  }

  @Test
  public void whenDatabaseTableNameUsed_thenExceptionIsThrown() {
    Customer customer = new Customer();
    customer.setEmail("john@baeldung.com");
    session.persist(customer);

    session.getTransaction().commit();
    session.close();

    session = sessionFactory.openSession();
    session.beginTransaction();

    assertThrows(IllegalArgumentException.class, () -> {
      session.createQuery("FROM customer", Customer.class);
    });
  }

  @Test
  public void whenEntityTableNameUsed_thenExceptionIsNotThrown() {
    Customer customer = new Customer();
    customer.setEmail("john@baeldung.com");
    session.persist(customer);

    session.getTransaction().commit();
    session.close();

    session = sessionFactory.openSession();
    session.beginTransaction();

    assertDoesNotThrow(() -> session.createQuery("FROM Customer", Customer.class));
  }

  @AfterEach
  public void closeSession() {
    session.close();
  }

  @AfterAll
  public static void afterTests() {
    sessionFactory.close();
  }

}
