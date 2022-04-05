package com.baeldung.transaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.config.MongoReactiveConfig;
import com.baeldung.model.User;

/**
 * 
 * This test requires:
 * * mongodb instance running on the environment
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoReactiveConfig.class)
public class MongoTransactionReactiveLiveTest {

    @Autowired
    private ReactiveMongoOperations reactiveOps;

    @Before
    public void testSetup() {
        if (!reactiveOps.collectionExists(User.class)
            .block()) {
            reactiveOps.createCollection(User.class);
        }
    }

    @After
    public void tearDown() {
        System.out.println(reactiveOps.findAll(User.class)
            .count()
            .block());
        reactiveOps.dropCollection(User.class);
    }

    @Test
    public void whenPerformTransaction_thenSuccess() {
        User user1 = new User("Jane", 23);
        User user2 = new User("John", 34);
        reactiveOps.inTransaction()
            .execute(action -> action.insert(user1)
                .then(action.insert(user2)));
    }
    
}
