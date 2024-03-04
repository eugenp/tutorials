package com.baeldung.hibernate.softdelete;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.h2.Driver;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.hibernate.softdelete.model.SoftDeletePerson;

public class SoftDeletePersonIntegrationTest {

    private static SessionFactory sessionFactory;

    private static Session session;

    SoftDeletePerson person1 = new SoftDeletePerson();
    SoftDeletePerson person2 = new SoftDeletePerson();

    @BeforeAll
    public static void beforeTests() {
        Configuration configuration = new Configuration().addAnnotatedClass(SoftDeletePerson.class)
            .setProperty("hibernate.dialect", H2Dialect.class.getName())
            .setProperty("hibernate.connection.driver_class", Driver.class.getName())
            .setProperty("hibernate.connection.url", "jdbc:h2:mem:test")
            .setProperty("hibernate.connection.username", "sa")
            .setProperty("hibernate.connection.password", "")
            .setProperty("hibernate.hbm2ddl.auto", "update")
            .setProperty("hibernate.show_sql", "true");

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
            .build();

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    @BeforeEach
    public void setup() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        SoftDeletePerson person1 = new SoftDeletePerson();
        person1.setName("Person1");
        List<String> emailIds = new ArrayList<>();
        emailIds.add("id1@dummy.com");
        emailIds.add("id2@dummy.com");
        person1.setEmailIds(emailIds);
        SoftDeletePerson person2 = new SoftDeletePerson();
        person2.setName("Person2");
        List<String> emailIdsPerson2 = new ArrayList<>();
        emailIdsPerson2.add("person2Id1@dummy.com");
        emailIdsPerson2.add("person2Id2@dummy.com");
        person2.setEmailIds(emailIdsPerson2);
        session.save(person1);
        session.save(person2);
        session.getTransaction()
            .commit();

        assertNotNull(person1.getName());
        assertNotNull(person2.getName());
        System.out.println(person1);
        System.out.println(person2);
    }

    @Test
    void whenDeletingUsingSoftDelete_ThenEntityAndCollectionAreDeleted() {
        session.beginTransaction();
        person1 = session.createQuery("from SoftDeletePerson where name='Person1'", SoftDeletePerson.class)
            .getSingleResult();
        person2 = session.createQuery("from SoftDeletePerson where name='Person2'", SoftDeletePerson.class)
            .getSingleResult();

        assertNotNull(person1);
        assertNotNull(person2);

        session.delete(person2);
        List<String> emailIds = person1.getEmailIds();
        emailIds.remove(0);
        person1.setEmailIds(emailIds);
        session.save(person1);
        session.getTransaction()
            .commit();
        List<SoftDeletePerson> activeRows = session.createQuery("from SoftDeletePerson")
            .list();
        List<SoftDeletePerson> deletedRows = session.createNamedQuery("getDeletedPerson", SoftDeletePerson.class)
            .getResultList();
        session.close();

        assertNotNull(person1.getName());
        System.out.println("-------------Active Rows-----------");
        activeRows.forEach(row -> System.out.println(row));
        System.out.println("-------------Deleted Rows-----------");
        deletedRows.forEach(row -> System.out.println(row));
    }

    @AfterAll
    static void afterTests() {
        sessionFactory.close();
    }
}
