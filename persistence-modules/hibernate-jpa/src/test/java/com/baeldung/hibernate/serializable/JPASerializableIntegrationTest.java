package com.baeldung.hibernate.serializable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JPASerializableIntegrationTest {

    private static EntityManager entityManager;

    @Before
    public void setUp() throws IOException {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("com.baeldung.hibernate.serializable.h2_persistence_unit");
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    @Test
    public void givenUser_whenPersisted_thenUserWillBeFoundById() {
        UserId userId = new UserId();
        userId.setName("John");
        userId.setLastName("Doe");
        Email email = new Email();
        email.setId(1);
        email.setName("johndoe");
        email.setDomain("gmail.com");
        User user = new User(userId, email);

        entityManager.persist(user);

        User userDb = entityManager.find(User.class, userId);
        assertEquals(userDb.getEmail().getName(), "johndoe");
    }

@Test
public void givenAssociation_whenPersisted_thenMultipleAccountsWillBeFoundByEmail() {
    UserId userId = new UserId();
    userId.setName("John");
    userId.setLastName("Doe");
    Email email = new Email();
    email.setId(1);
    email.setName("johndoe");
    email.setDomain("gmail.com");
    User user = new User(userId, email);
    Account account = new Account();
    account.setType("test");
    account.setId(10);
    account.setUser(user);
    Account account2 = new Account();
    account2.setType("main");
    account2.setId(11);
    account2.setUser(user);

    entityManager.persist(user);
    entityManager.persist(account);
    entityManager.persist(account2);

    List userAccounts = entityManager.createQuery("select a from Account a join fetch a.user where a.user.email = :email")
            .setParameter("email", email).getResultList();
    assertEquals(userAccounts.size(), 2);
}

    @After
    public void tearDown() {
        entityManager.close();
    }

}
