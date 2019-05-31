package com.baeldung.hexagonal.jpa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.baeldung.hexagonal.domain.CreatePersonRequest;
import com.baeldung.hexagonal.domain.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Integration test which verifies that {@link PersonEntityRepository} can save entities to H2 DB.
 */
class PersonEntityRepositoryIntegrationTest {

    private EntityManagerFactory emf;

    @BeforeEach
    void before() {
        emf = Persistence.createEntityManagerFactory("com.baeldung.hexagonal");
    }

    @Test
    void givenSavedNewPerson_whenRetrieveById_thenRetrievesExpectedPerson() {
        // save person
        final CreatePersonRequest request = new CreatePersonRequest("Joe", "Schmoe");
        final Person expected = new PersonEntityFactory().create(request);
        EntityManager em = emf.createEntityManager();
        em.getTransaction()
            .begin();
        em.persist(expected);
        em.getTransaction()
            .commit();
        em.close();

        // retrieve person
        em = emf.createEntityManager();
        final Person actual = em.find(PersonEntity.class, expected.getId());
        em.close();

        // assert equal
        assertEquals(actual, expected);
    }

    @AfterEach
    void after() {
        emf.close();
    }
}
