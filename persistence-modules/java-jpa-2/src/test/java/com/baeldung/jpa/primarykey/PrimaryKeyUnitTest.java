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
    public void givenUser_whenSave_thenReturnPrimaryKey() {
        User user = new User();
        user.setUserName("TestName");
        
        entityManager.persist(user);

        Assert.assertEquals(Long.valueOf(1), user.getId());
    }
}