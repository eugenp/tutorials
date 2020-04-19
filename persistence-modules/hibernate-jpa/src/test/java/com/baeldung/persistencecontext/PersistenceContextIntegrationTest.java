package com.baeldung.persistencecontext;

import com.baeldung.persistencecontext.entity.User;
import com.baeldung.persistencecontext.service.ExtendedPersistenceContextUserService;
import com.baeldung.persistencecontext.service.TransctionPersistenceContextUserService;

import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.baeldung.persistencecontext.PersistenceContextDemoApplication.class)
public class PersistenceContextIntegrationTest {

    @Autowired
    private TransctionPersistenceContextUserService transctionPersistenceContext;
    @Autowired
    private ExtendedPersistenceContextUserService extendedPersistenceContext;

    @Test
    public void testThatWhenUserSavedWithTransctionPersistenceContextThenUserShouldGetSavedInDB() {
        User user = new User(121L, "Devender", "admin");
        transctionPersistenceContext.insertWithTransaction(user);

        User userFromTransctionPersistenceContext = transctionPersistenceContext.find(user.getId());
        assertNotNull(userFromTransctionPersistenceContext);

        User userFromExtendedPersistenceContext = extendedPersistenceContext.find(user.getId());
        assertNotNull(userFromExtendedPersistenceContext);
    }

    @Test(expected = TransactionRequiredException.class)
    public void testThatUserSaveWithoutTransactionThrowException() {
        User user = new User(122L, "Devender", "admin");
        transctionPersistenceContext.insertWithoutTransaction(user);
    }

    @Test
    public void testThatWhenUserSavedWithExtendedPersistenceContextWithoutTransactionThenUserShouldGetCached() {
        User user = new User(123L, "Devender", "admin");
        extendedPersistenceContext.insertWithoutTransaction(user);

        User userFromExtendedPersistenceContext = extendedPersistenceContext.find(user.getId());
        assertNotNull(userFromExtendedPersistenceContext);

        User userFromTransctionPersistenceContext = transctionPersistenceContext.find(user.getId());
        assertNull(userFromTransctionPersistenceContext);
    }

    @Test(expected = EntityExistsException.class)
    public void testThatPersistUserWithSameIdentifierThrowException() {
        User user1 = new User(126L, "Devender", "admin");
        User user2 = new User(126L, "Devender", "admin");
        extendedPersistenceContext.insertWithoutTransaction(user1);
        extendedPersistenceContext.insertWithoutTransaction(user2);
    }

    @Test
    public void testThatWhenUserSavedWithExtendedPersistenceContextWithTransactionThenUserShouldSaveEntityIntoDB() {
        User user = new User(127L, "Devender", "admin");
        extendedPersistenceContext.insertWithTransaction(user);

        User userFromDB = transctionPersistenceContext.find(user.getId());
        assertNotNull(userFromDB);
    }

    @Test
    public void testThatWhenUserSavedWithExtendedPersistenceContextWithTransactionThenUserShouldFlushCachedEntityIntoDB() {
        User user1 = new User(124L, "Devender", "admin");
        extendedPersistenceContext.insertWithoutTransaction(user1);

        User user2 = new User(125L, "Devender", "admin");
        extendedPersistenceContext.insertWithTransaction(user2);

        User user1FromTransctionPersistenceContext = transctionPersistenceContext.find(user1.getId());
        assertNotNull(user1FromTransctionPersistenceContext);

        User user2FromTransctionPersistenceContext = transctionPersistenceContext.find(user2.getId());
        assertNotNull(user2FromTransctionPersistenceContext);
    }

}
