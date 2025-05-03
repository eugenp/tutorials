package com.baeldung.hibernate.columnduplicatedmapping;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.hibernate.DuplicateMappingException;
import org.hibernate.Session;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ColumnDuplicatedMappingUnitTest {

    private static Session session;

    @Test
    @Disabled("Enable this test case once you uncomment the column mapping in Person entity class")
    void whenDuplicatingColumnMapping_thenThrowDuplicateMappingException() {
        assertThatThrownBy(() -> {
            session = HibernateUtil.getSessionFactory()
                .openSession();
            session.beginTransaction();

            session.createQuery("FROM Person", Person.class)
                .list();

            session.close();
        }).isInstanceOf(DuplicateMappingException.class)
            .hasMessageContaining("Column 'first_name' is duplicated in mapping for entity");
    }

    @Test
    void whenNotDuplicatingColumnMapping_thenCorrect() {
        session = HibernateUtil.getSessionFactory()
            .openSession();
        session.beginTransaction();

        assertThat(session.createQuery("FROM Person", Person.class)
            .list()).isEmpty();

        session.close();
    }

}
