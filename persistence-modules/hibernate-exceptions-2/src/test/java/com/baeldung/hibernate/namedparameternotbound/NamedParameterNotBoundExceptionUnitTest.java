package com.baeldung.hibernate.namedparameternotbound;

import com.baeldung.hibernate.exception.persistentobject.HibernateUtil;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NamedParameterNotBoundExceptionUnitTest {

    private static Session session;

    @BeforeAll
    static void init() {
        session = HibernateUtil.getSessionFactory()
          .openSession();
        session.beginTransaction();
    }

    @AfterAll
    static void clear() {
        session.close();
    }

    @Test
    void whenSettingValueToNamedParameter_thenDoNotThrowQueryException() {
        Query<Person> query = session.createQuery("FROM Person p WHERE p.firstName = :firstName", Person.class);
        query.setParameter("firstName", "Azhrioun");

        assertNotNull(query.list());
    }

    @Test
    void whenNotSettingValueToNamedParameter_thenThrowQueryException() {
        Exception exception = assertThrows(QueryException.class, () -> {
            Query<Person> query = session.createQuery("FROM Person p WHERE p.firstName = :firstName", Person.class);
            query.list();
        });

        String expectedMessage = "No argument for named parameter";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
