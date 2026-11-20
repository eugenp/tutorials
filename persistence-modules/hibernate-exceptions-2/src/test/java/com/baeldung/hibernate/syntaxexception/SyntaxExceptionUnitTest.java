package com.baeldung.hibernate.syntaxexception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.SyntaxException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SyntaxExceptionUnitTest {

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
    void whenUsingInvalidHQLSyntax_thenThrowSyntaxException() {
        assertThatThrownBy(() -> {
            session.createQuery("SELECT * FROM Person p", Person.class)
                .list();
        }).hasRootCauseInstanceOf(SyntaxException.class)
            .hasMessageContaining("token '*', no viable alternative");
    }

    @Test
    void whenUsingValidHQLSyntax_thenCorrect() {
        Person person = new Person(1, "Jane", "Austen");
        session.persist(person);
        
        List<Person> personList = session.createQuery("SELECT p FROM Person p", Person.class).list();
        
        assertThat(personList).contains(person);
    }

}
