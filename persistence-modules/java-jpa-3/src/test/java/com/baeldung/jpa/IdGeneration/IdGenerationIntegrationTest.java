package com.baeldung.jpa.IdGeneration;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.UUID;

public class IdGenerationIntegrationTest {

    private static EntityManager entityManager;
    private static UserService service;

    @BeforeClass
    public static void setup() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa-h2-id-generation");
        entityManager = factory.createEntityManager();
        service = new UserService(entityManager);
    }

    @Test
    public void whenNewUserIsPersisted_thenEntityHasNoId() {
        User user = new User();
        user.setUsername("test");
        user.setPassword(UUID.randomUUID().toString());

        long index = service.saveUser(user);
        Assert.assertEquals(0L, index);
    }

    @Test
    public void whenTransactionIsControlled_thenEntityHasId() {
        User user = new User();
        user.setUsername("test");
        user.setPassword(UUID.randomUUID().toString());

        entityManager.getTransaction().begin();
        long index = service.saveUser(user);
        entityManager.getTransaction().commit();

        Assert.assertEquals(2L, index);
    }

}
