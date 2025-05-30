package com.baeldung.hibernate.unknownentityexception;

import com.baeldung.hibernate.exception.persistentobject.HibernateUtil;
import com.baeldung.hibernate.namedparameternotbound.Person;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.query.sqm.UnknownEntityException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UnknownEntityExceptionUnitTest {

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
    void whenUsingUnknownEntity_thenThrowUnknownEntityException() {
        assertThatThrownBy(() -> session.createQuery("FROM PERSON", Person.class))
                .hasRootCauseInstanceOf(UnknownEntityException.class)
                .hasRootCauseMessage("Could not resolve root entity 'PERSON'");
    }

    @Test
    void whenUsingCorrectEntity_thenReturnResult() {
        Query<Person> query = session.createQuery("FROM Person", Person.class);

        assertThat(query.list()).isEmpty();
    }

}
