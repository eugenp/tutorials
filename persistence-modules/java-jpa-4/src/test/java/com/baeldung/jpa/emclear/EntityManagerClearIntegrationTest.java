package com.baeldung.jpa.emclear;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;

import static jakarta.persistence.Persistence.createEntityManagerFactory;
import static org.assertj.core.api.Assertions.assertThat;

class EntityManagerClearIntegrationTest {

    private static final String DEFAULT_NAME = "David Jones";
    private static final String MASKED_NAME = "*****";

    private static final EntityManagerFactory factory = createEntityManagerFactory("jpa-emclear");
    private EntityManager entityManager;

    @BeforeEach
    void createEntityManager() {
        entityManager = factory.createEntityManager();
    }

    @Test
    void givenAnEntityPersistedByEm_whenUpdateEntityByJdbcWithoutClearEm_thenRetrievedEntityIsNotUpdated() {
        Person newPerson = insertPersonByEntityManager();
        maskPersonNameByJdbc(newPerson.getId());

        Person updatedPerson = entityManager.find(Person.class, newPerson.getId());
        assertThat(updatedPerson.getName()).isNotEqualTo(MASKED_NAME);
    }

    @Test
    void givenAnEntityPersistedByEm_whenUpdateEntityByJdbcWithClearEm_thenRetrievedEntityIsUpdated() {
        Person newPerson = insertPersonByEntityManager();
        maskPersonNameByJdbc(newPerson.getId());

        entityManager.clear();

        Person updatedPerson = entityManager.find(Person.class, newPerson.getId());
        assertThat(updatedPerson.getName()).isEqualTo(MASKED_NAME);
    }

    private Person insertPersonByEntityManager() {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Person person = new Person();
        person.setName(DEFAULT_NAME);
        entityManager.persist(person);

        transaction.commit();
        return person;
    }

    private void maskPersonNameByJdbc(long id) {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            try (PreparedStatement pStmt = connection.prepareStatement("UPDATE person SET name=? WHERE id=?")) {
                pStmt.setString(1, MASKED_NAME);
                pStmt.setLong(2, id);
                pStmt.executeUpdate();
            }
        });
    }

}