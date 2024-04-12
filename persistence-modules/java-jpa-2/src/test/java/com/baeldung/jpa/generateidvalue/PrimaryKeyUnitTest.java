package com.baeldung.jpa.generateidvalue;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * PrimaryKeyGeneratorTest class
 * 
 * @author shiwangzhihe@gmail.com
 */
public class PrimaryKeyUnitTest {
    private static EntityManager entityManager;

    @BeforeClass
    public static void setup() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa-h2-primarykey");
        entityManager = factory.createEntityManager();
    }

    @Test
    public void givenIdentityStrategy_whenCommitTransction_thenReturnPrimaryKey() {
        User user = new User();
        user.setName("TestName");

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        Assert.assertNull(user.getId());
        entityManager.getTransaction().commit();

        Long expectPrimaryKey = 1L;
        Assert.assertEquals(expectPrimaryKey, user.getId());
    }

    @Test
    public void givenTableStrategy_whenPersist_thenReturnPrimaryKey() {
        Task task = new Task();
        task.setName("Test Task");

        entityManager.getTransaction()
            .begin();
        entityManager.persist(task);
        Long expectPrimaryKey = 10000L;
        Assert.assertEquals(expectPrimaryKey, task.getId());

        entityManager.getTransaction()
            .commit();
    }

    @Test
    public void givenSequenceStrategy_whenPersist_thenReturnPrimaryKey() {
        Article article = new Article();
        article.setName("Test Name");

        entityManager.getTransaction()
            .begin();
        entityManager.persist(article);
        Long expectPrimaryKey = 51L;
        Assert.assertEquals(expectPrimaryKey, article.getId());

        entityManager.getTransaction()
            .commit();
    }

    @Test
    public void givenAutoStrategy_whenPersist_thenReturnPrimaryKey() {
        Admin admin = new Admin();
        admin.setName("Test Name");

        entityManager.persist(admin);

        Long expectPrimaryKey = 1L;
        Assert.assertEquals(expectPrimaryKey, admin.getId());
    }
}