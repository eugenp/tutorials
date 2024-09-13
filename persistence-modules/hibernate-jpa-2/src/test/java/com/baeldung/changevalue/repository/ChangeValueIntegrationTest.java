package com.baeldung.changevalue.repository;

import com.baeldung.changevalue.entity.Student;
import com.baeldung.changevalue.entity.StudentWithColumnTransformer;
import com.baeldung.changevalue.entity.StudentWithEventListener;
import com.baeldung.changevalue.entity.StudentWithHibernateEvent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
class ChangeValueIntegrationTest {

    private static int recordIndex = 0;

    @PersistenceContext
    private EntityManager entityManager;

    @BeforeEach
    void setup() {
        recordIndex++;
    }

    @Test
    void whenPersistStudent_thenNameIsInUpperCase() {
        Student student = new Student();
        student.setName("David Morgan");

        entityManager.persist(student);

        // then
        student = entityManager.find(Student.class, recordIndex);
        assertThat(student.getName()).isEqualTo("DAVID MORGAN");
    }

    @Test
    void whenPersistStudentEventListener_thenNameIsInUpperCase() {
        StudentWithEventListener student = new StudentWithEventListener();
        student.setName("David Morgan");

        entityManager.persist(student);

        // then
        student = entityManager.find(StudentWithEventListener.class, recordIndex);
        assertThat(student.getName()).isEqualTo("DAVID MORGAN");
    }

    @Test
    void whenPersistStudentHibernateEvent_thenNameIsInUpperCase() {
        StudentWithHibernateEvent student = new StudentWithHibernateEvent();
        student.setName("David Morgan");

        entityManager.persist(student);

        // then
        student = entityManager.find(StudentWithHibernateEvent.class, recordIndex);
        assertThat(student.getName()).isEqualTo("DAVID MORGAN");
    }

    @Test
    void whenPersistStudentWithoutEmClear_thenNameIsNotInUpperCase() {
        // given
        StudentWithColumnTransformer student = new StudentWithColumnTransformer();
        student.setName("David Morgan");

        entityManager.persist(student);

        // then
        student = entityManager.find(StudentWithColumnTransformer.class, recordIndex);
        assertThat(student.getName()).isNotEqualTo("DAVID MORGAN");
    }

    @Test
    void whenPersistStudentWithEmClear_thenNameIsInUpperCase() {
        // given
        StudentWithColumnTransformer student = new StudentWithColumnTransformer();
        student.setName("David Morgan");

        entityManager.persist(student);
        entityManager.clear();

        // then
        student = entityManager.find(StudentWithColumnTransformer.class, recordIndex);
        assertThat(student.getName()).isEqualTo("DAVID MORGAN");
    }

}