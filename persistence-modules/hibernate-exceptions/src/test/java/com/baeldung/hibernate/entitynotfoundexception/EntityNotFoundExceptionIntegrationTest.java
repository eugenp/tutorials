package com.baeldung.hibernate.entitynotfoundexception;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import java.io.IOException;

public class EntityNotFoundExceptionIntegrationTest {

    private static EntityManager entityManager;

    @Before
    public void setUp() throws IOException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("com.baeldung.hibernate.entitynotfoundexception.h2_persistence_unit");
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

    }


    @Test(expected = EntityNotFoundException.class)
    public void givenNonExistingUserId_whenGetReferenceIsUsed_thenExceptionIsThrown() {
        User user = entityManager.getReference(User.class, 1L);
        user.getName();
    }

    @Test(expected = EntityNotFoundException.class)
    public void givenItem_whenManyToOneEntityIsMissing_thenExceptionIsThrown() {
        entityManager.createNativeQuery("Insert into Item (category_id, name, id) values (1, 'test', 1)").executeUpdate();
        entityManager.flush();
        Item item = entityManager.find(Item.class, 1L);
        item.getCategory().getName();
    }

    @After
    public void tearDown() {
        entityManager.close();
    }

}
