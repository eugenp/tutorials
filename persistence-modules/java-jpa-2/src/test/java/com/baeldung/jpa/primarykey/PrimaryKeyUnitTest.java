package com.baeldung.jpa.primarykey;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
    public void givenNewUser_whenSave_thenReturnPrimaryKey() {
        User user = new User();
        user.setUserName("TestName");
        
        entityManager.persist(user);

        Long expectPrimaryKey = 1L;
        Assert.assertEquals(expectPrimaryKey, user.getId());
    }

    @Test
    public void givenNewTask_whenSave_thenReturnPrimaryKey() {
        Task task = new Task();
        task.setName("Test Task");

        entityManager.persist(task);

        Long expectPrimaryKey = 10000L;
        Assert.assertEquals(expectPrimaryKey, task.getId());
    }

    @Test
    public void givenNewArticle_whenSave_thenReturnPrimaryKey() {
        Article article = new Article();
        article.setContent("Test Content");
        article.setTitle("Test Title");

        entityManager.persist(article);

        Long expectPrimaryKey = 10L;
        Assert.assertEquals(expectPrimaryKey, article.getId());
    }

    @Test
    public void givenNewAdmin_whenSave_thenReturnPrimaryKey() {
        Admin admin = new Admin();
        admin.setAdminName("Test Name");
        admin.setEmail("test@email.com");

        entityManager.persist(admin);

        Long expectPrimaryKey = 1L;
        Assert.assertEquals(expectPrimaryKey, admin.getId());
    }
}