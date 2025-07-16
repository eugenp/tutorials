package com.baeldung.hibernate.annotationexception;

import static org.assertj.core.api.Assertions.assertThat;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Test;

class ColumnNotAllowedOnManyToOneUnitTest {

    /*
     * uncomment this test case to test the exception
    @Test
    void whenUsingColumnAnnotationWithManyToOneAnnotation_thenThrowAnnotationException() {
        assertThatThrownBy(() -> {
            HibernateUtil.getSessionFactory()
                .openSession();
        }).isInstanceOf(AnnotationException.class)
            .hasMessageContaining("university' is a '@ManyToOne' association and may not use '@Column'");
    }
    */

    @Test
    void whenNotUsingColumnAnnotationWithManyToOneAnnotation_thenCorrect() {
        Session session = HibernateUtil.getSessionFactory()
            .openSession();

        Query<Student> query = session.createQuery("FROM Student", Student.class);

        assertThat(query.list()).isEmpty();

        session.close();
    }

}
