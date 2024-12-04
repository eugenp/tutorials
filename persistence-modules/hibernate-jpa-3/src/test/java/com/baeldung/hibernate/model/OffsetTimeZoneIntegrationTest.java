package com.baeldung.hibernate.model;

import com.baeldung.hibernate.timezonecolumn.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.H2Dialect;
import org.junit.jupiter.api.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OffsetTimeZoneIntegrationTest {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    @BeforeAll
    public static void beforeTests() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", H2Dialect.class.getName());
        properties.setProperty("hibernate.connection.driver_class", "com.p6spy.engine.spy.P6SpyDriver");
        properties.setProperty("hibernate.connection.url", "jdbc:p6spy:h2:mem:test");
        properties.setProperty("hibernate.connection.username", "sa");
        properties.setProperty("hibernate.connection.password", "");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.use_sql_comments", "true");
        properties.setProperty("hibernate.jdbc.bind_param", "true");
        properties.setProperty("hibernate.type", "TRACE");
        properties.setProperty("hibernate.orm.jdbc.bind","TRACE");
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);
        configuration.setProperties(properties);

                /**/;

        entityManagerFactory = Persistence.createEntityManagerFactory("hibernate_persistence", properties);

    }

    @BeforeEach
    public void setup() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterEach
    public void deleteDataAfterEachTest() {
        // Delete all records from the Person table after each test case
        entityManager.getTransaction().begin();
        entityManager.createQuery("DELETE FROM Person").executeUpdate();
        entityManager.getTransaction().commit();
    }

    @Test
    void whenPersistingOffsetDateTime_thenIsPersisted() {
        entityManager.getTransaction().begin();
        Person person1 = new Person();
        person1.setInsertedAt(OffsetDateTime.of(2024,10,31,12,0,0,0, ZoneOffset.ofHours(5)));
        entityManager.persist(person1);

        Person savedEntity = entityManager.createQuery("from Person", Person.class)
                .getSingleResult();
        assertNotNull(savedEntity);
        entityManager.getTransaction().commit();
    }

    @Test
    void whenPersistingOffsetDateTimeWithTimeZoneColumn_thenIsPersisted() {
        entityManager.getTransaction().begin();
        Person person1 = new Person();
        person1.setInsertedAt(OffsetDateTime.of(2024,10,30,12,0,0,0, ZoneOffset.ofHours(5)));
        person1.setLastUpdatedAt(OffsetDateTime.of(2024,10,31,12,0,0,0, ZoneOffset.ofHours(5)));
        entityManager.persist(person1);

        Person savedEntity = entityManager.createQuery("from Person", Person.class)
                .getSingleResult();
        assertNotNull(savedEntity);
        entityManager.getTransaction().commit();
    }

    @AfterAll
    static void afterTests() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
