package com.baeldung.hibernate.noargumentforordinalparameter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class NoArgumentForOrdinalParameterUnitTest {

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
    void whenMissingParameter_thenThrowQueryParameterException() {
        assertThatThrownBy(() -> {
            String selectQuery = """
                FROM Employee 
                WHERE firstName = ?1 
                AND lastName = ?2
            """;
            Query<Employee> query = session.createQuery(selectQuery, Employee.class);
            query.setParameter(1, "Jean");

            query.list();
        }).isInstanceOf(QueryException.class)
            .hasMessageContaining("No argument for ordinal parameter");
    }

    @Test
    void whenDefiningAllParameters_thenCorrect() {
        String selectQuery = """
            FROM Employee
            WHERE firstName = ?1 
            AND lastName = ?2
        """;
        Query<Employee> query = session.createQuery(selectQuery, Employee.class);
        query.setParameter(1, "Jean")
            .setParameter(2, "Smith");

        assertThat(query.list()).isNotNull();
    }

}
