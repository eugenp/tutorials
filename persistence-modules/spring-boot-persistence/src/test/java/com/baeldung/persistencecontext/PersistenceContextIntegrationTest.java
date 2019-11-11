package com.baeldung.persistencecontext;

import com.baeldung.persistencecontext.entity.User;
import com.baeldung.persistencecontext.service.ExtendedPersistenceContextUserService;
import com.baeldung.persistencecontext.service.TransctionPersistenceContextUserService;

import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersistenceContextDemoApplication.class)
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
        User userFromExtendedPersistenceContext = extendedPersistenceContext.find(user.getId());
        assertThat(userFromTransctionPersistenceContext, Is.is(IsNull.notNullValue()));
        assertThat(userFromExtendedPersistenceContext, Is.is(IsNull.notNullValue()));
    }

    @Test(expected = TransactionRequiredException.class)
    public void testThatWhenUserSaveWithOutTransactionowTransactionRequiredExceptionOnPersist() {
        User user = new User(122L, "Devender", "admin");
        transctionPersistenceContext.insertWithoutTransaction(user);
    }

    @Test
    public void testThatWhenUserSavedWithExtendedPersistenceContextWithoutTransactionThenUserShouldGetCached() {
        User user = new User(123L, "Devender", "admin");
        extendedPersistenceContext.insertWithoutTransaction(user);
        User userFromExtendedPersistenceContext = extendedPersistenceContext.find(user.getId());
        User userFromTransctionPersistenceContext = transctionPersistenceContext.find(user.getId());
        assertThat(userFromExtendedPersistenceContext, Is.is(IsNull.notNullValue()));
        assertThat(userFromTransctionPersistenceContext, Is.is(IsNull.nullValue()));
    }

    @Test(expected = EntityExistsException.class)
    public void testThatWhenAddUserWithSameIdentifierInPersistenceContextThenShouldThrowException() {
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
        assertThat(userFromDB, Is.is(IsNull.notNullValue()));
    }

    @Test
    public void testThatWhenUserSavedWithExtendedPersistenceContextWithTransactionThenUserShouldFlushCachedEntityIntoDB() {
        User user1 = new User(124L, "Devender", "admin");
        extendedPersistenceContext.insertWithoutTransaction(user1);
        User user2 = new User(125L, "Devender", "admin");
        extendedPersistenceContext.insertWithTransaction(user2);
        User userFromExtendedPersistenceContextuser1 = extendedPersistenceContext.find(user1.getId());
        User userFromExtendedPersistenceContextuser2 = extendedPersistenceContext.find(user2.getId());
        User userFromTransctionPersistenceContextuser1 = transctionPersistenceContext.find(user1.getId());
        User userFromTransctionPersistenceContextuser2 = transctionPersistenceContext.find(user2.getId());
        assertThat(userFromExtendedPersistenceContextuser1, Is.is(IsNull.notNullValue()));
        assertThat(userFromExtendedPersistenceContextuser2, Is.is(IsNull.notNullValue()));
        assertThat(userFromTransctionPersistenceContextuser1, Is.is(IsNull.notNullValue()));
        assertThat(userFromTransctionPersistenceContextuser2, Is.is(IsNull.notNullValue()));
    }

}
