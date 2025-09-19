package com.baeldung.hibernate.syntaxexception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.hibernate.Session;
import org.hibernate.query.SyntaxException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.baeldung.hibernate.exception.persistentobject.HibernateUtil;

class QueryExceptionUnitTest {

    private static Session session;

    @BeforeAll
    static void beforeAll() {
        session = HibernateUtil.getSessionFactory()
            .openSession();
        session.beginTransaction();
    }

    @AfterAll
    static void afterAll() {
        session.close();
    }

    @Test
    void whenUsingInvalidHQLSyntax_thenThrowSyntaxExceptionn() {
        assertThatThrownBy(() -> {
            session.createQuery("SELECT * FROM Person p", Person.class)
                .list();
        }).hasRootCauseInstanceOf(SyntaxException.class)
            .hasMessageContaining("token '*', no viable alternative");
    }

    @Test
    void whenUsingValidHQLSyntax_thenCorrect() {
        assertThat(session.createQuery("FROM Person", Person.class)
            .list()).isEmpty();
    }

}
