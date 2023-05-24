package com.baeldung.hibernate.creationupdatetimestamp;

import static org.hibernate.FlushMode.MANUAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.h2.Driver;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.baeldung.hibernate.creationupdatetimestamp.model.Book;

class HibernateCreationUpdateTimestampIntegrationTest {

    private static SessionFactory sessionFactory;

    private Session session;

    @BeforeAll
    public static void beforeTests() {
        Configuration configuration = new Configuration().addAnnotatedClass(Book.class)
          .setProperty("hibernate.dialect", H2Dialect.class.getName())
          .setProperty("hibernate.connection.driver_class", Driver.class.getName())
          .setProperty("hibernate.connection.url", "jdbc:h2:mem:test")
          .setProperty("hibernate.connection.username", "sa")
          .setProperty("hibernate.connection.password", "")
          .setProperty("hibernate.hbm2ddl.auto", "update");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
          .build();

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    @Test
    void whenCreatingEntity_ThenCreatedOnIsSet() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Book book = new Book();

        session.save(book);
        session.getTransaction()
          .commit();
        session.close();

        assertNotNull(book.getCreatedOn());
    }

    @Test
    void whenCreatingEntity_ThenCreatedOnAndLastUpdatedOnAreBothSet() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Book book = new Book();

        session.save(book);
        session.getTransaction()
          .commit();
        session.close();

        assertNotNull(book.getCreatedOn());
        assertNotNull(book.getLastUpdatedOn());
    }

    @Test
    void whenCreatingEntity_ThenCreatedOnAndLastUpdatedOnAreEqual() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        Book book = new Book();

        session.save(book);
        session.getTransaction().commit();
        session.close();

        Date createdOn = Date.from(book.getCreatedOn());
        Date lastUpdatedOn = Date.from(book.getLastUpdatedOn());
        SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy HH:mm:ss");

        assertEquals(formatter.format(createdOn), formatter.format(lastUpdatedOn));
    }

    @Test
    void whenUpdatingEntity_ThenLastUpdatedOnIsUpdatedAndCreatedOnStaysTheSame() {
        session = sessionFactory.openSession();
        session.setHibernateFlushMode(MANUAL);
        session.beginTransaction();
        Book book = new Book();
        session.save(book);
        session.flush();
        Instant createdOnAfterCreation = book.getCreatedOn();
        Instant lastUpdatedOnAfterCreation = book.getLastUpdatedOn();

        String newName = "newName";
        book.setTitle(newName);
        session.save(book);
        session.flush();
        session.getTransaction().commit();
        session.close();
        Instant createdOnAfterUpdate = book.getCreatedOn();
        Instant lastUpdatedOnAfterUpdate = book.getLastUpdatedOn();

        assertEquals(newName, book.getTitle());
        assertNotNull(createdOnAfterUpdate);
        assertNotNull(lastUpdatedOnAfterUpdate);
        assertEquals(createdOnAfterCreation, createdOnAfterUpdate);
        assertNotEquals(lastUpdatedOnAfterCreation, lastUpdatedOnAfterUpdate);
    }

    @AfterAll
    static void afterTests() {
        sessionFactory.close();
    }
}
