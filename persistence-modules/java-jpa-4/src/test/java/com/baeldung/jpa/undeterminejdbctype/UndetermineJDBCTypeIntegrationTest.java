package com.baeldung.jpa.undeterminejdbctype;

import static jakarta.persistence.Persistence.createEntityManagerFactory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
public class UndetermineJDBCTypeIntegrationTest {

    private EntityManager entityManager;


    @Test
    void givenEntityWithMapAttribute_whenInitialize_thenThrowsException() {
        PersistenceException exception = assertThrows(PersistenceException.class,
            () -> createEntityManagerFactory("jpa-undeterminejdbctype-complextype"));
        assertThat(exception)
            .hasMessage("Could not determine recommended JdbcType for Java type 'java.util.Map<java.lang.String, java.lang.String>'");
    }

    @Test
    void givenEntityWithMapAttribute_whenInitializeWithJsonType_thenNoExceptionThrows() {
        EntityManagerFactory factory = createEntityManagerFactory("jpa-undeterminejdbctype-complextypewithJSON");
        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        StudentWithJson student = new StudentWithJson();
        Map<String, String> details = new HashMap<>();
        details.put("course", "Computer Science");
        details.put("year", "2024");
        student.setStudentDetails(details);

        entityManager.persist(student);
        entityManager.getTransaction().commit();

        // Retrieve and assert the student entity
        StudentWithJson retrievedStudent = entityManager.find(StudentWithJson.class, student.getId());
        assertEquals(student.getStudentDetails(), retrievedStudent.getStudentDetails());
    }

    @Test
    void givenEntityWithoutRelationshipDeclare_whenInitialize_throwsException() {
        PersistenceException exception = assertThrows(PersistenceException.class,
            () -> createEntityManagerFactory("jpa-undeterminejdbctype-relationship"));
        assertThat(exception)
            .hasMessage("Could not determine recommended JdbcType for Java type 'com.baeldung.jpa.undeterminejdbctype.Address'");
    }

    @Test
    void givenEntityWithProperRelationshipDeclare_whenInitialize_throwsException() {
        assertDoesNotThrow(() -> createEntityManagerFactory("jpa-undeterminejdbctype-annotatedrelationship"));
    }
}
