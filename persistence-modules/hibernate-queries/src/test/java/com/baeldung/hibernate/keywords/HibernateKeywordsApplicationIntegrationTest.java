package com.baeldung.hibernate.keywords;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import javax.persistence.PersistenceException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HibernateKeywordsApplicationIntegrationTest {

    private static SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    static void createSession() {
        sessionFactory = new Configuration().addAnnotatedClass(BrokenPhoneOrder.class)
          .addAnnotatedClass(PhoneOrder.class)
          .configure("keywords/hibernate.keywords.cfg.xml")
          .buildSessionFactory();
    }

    @BeforeEach
    void before() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void after() {
        session.close();
    }

    @Test
    void givenBrokenPhoneOrderWithReservedKeywords_whenNewObjectIsPersisted_thenItFails() {
        BrokenPhoneOrder order = new BrokenPhoneOrder(randomUUID().toString(), "My House");

        assertThatExceptionOfType(PersistenceException.class).isThrownBy(() -> {
            session.persist(order);
            session.flush();
        });
    }

    @Test
    void givenPhoneOrderWithEscapedKeywords_whenNewObjectIsPersisted_thenItSucceeds() {
        PhoneOrder order = new PhoneOrder(randomUUID().toString(), "here");

        session.persist(order);
        session.flush();
    }

}
