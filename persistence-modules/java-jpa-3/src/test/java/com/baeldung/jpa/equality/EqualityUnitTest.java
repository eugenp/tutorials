package com.baeldung.jpa.equality;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class EqualityUnitTest {

    private static EntityManagerFactory factory;
    private static EntityManager entityManager;

    @BeforeClass
    public static void setup() {
        factory = Persistence.createEntityManagerFactory("jpa-h2-equality");
        entityManager = factory.createEntityManager();
    }

    @Test
    public void givenObjectBasedEquality_whenUsingEquals_thenEqualIsBasedOnInstance() throws CloneNotSupportedException {
        EqualByJavaDefault object1 = new EqualByJavaDefault();
        EqualByJavaDefault object2 = new EqualByJavaDefault();

        object1.setEmail("test.user@domain.com");

        entityManager.getTransaction().begin();
        entityManager.persist(object1);
        entityManager.getTransaction().commit();

        object2 = (EqualByJavaDefault) object1.clone();

        Assert.assertNotEquals(object1, object2);
        Assert.assertEquals(object1.getId(), object2.getId());
        Assert.assertEquals(object1.getEmail(), object2.getEmail());
    }

    @Test
    public void givenIdBasedEquality_whenUsingEquals_thenEqualIsBasedOnId() {
        EqualById object1 = new EqualById();
        EqualById object2 = new EqualById();

        object1.setEmail("test.user.1@domain.com");
        object2.setEmail("test.user.2@domain.com");

        entityManager.getTransaction().begin();
        entityManager.persist(object1);
        entityManager.getTransaction().commit();

        object2.setId(object1.getId());

        Assert.assertEquals(object1, object2);
        Assert.assertEquals(object1.getId(), object2.getId());
        Assert.assertNotEquals(object1.getEmail(), object2.getEmail());
    }

    @Test
    public void givenBusinessKeyBasedEquality_whenUsingEquals_thenEqualIsBasedOnBusinessKey() {
        EqualByBusinessKey object1 = new EqualByBusinessKey();
        EqualByBusinessKey object2 = new EqualByBusinessKey();

        object1.setEmail("test.user@test-domain.com");
        object2.setEmail("test.user@test-domain.com");

        entityManager.getTransaction().begin();
        entityManager.persist(object1);
        entityManager.getTransaction().commit();

        Assert.assertEquals(object1, object2);
        Assert.assertNotEquals(object1.getId(), object2.getId());
    }
}
