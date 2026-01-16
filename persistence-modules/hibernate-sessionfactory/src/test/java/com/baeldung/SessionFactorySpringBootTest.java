package com.baeldung;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SessionFactorySpringBootTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    void givenSessionFactory_whenOpeningSession_thenSessionIsCreated() {

        try (Session session = sessionFactory.openSession()) {
            assertNotNull(session);
        }
    }
}