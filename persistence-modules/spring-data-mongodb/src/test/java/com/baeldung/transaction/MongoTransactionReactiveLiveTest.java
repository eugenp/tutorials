package com.baeldung.transaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.config.MongoReactiveConfig;
import com.baeldung.model.User;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

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

    @Autowired
    private TransactionalOperator transactionalOperator;

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

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

        Mono<User> saveEntity1 = mongoTemplate.save(user1);
        Mono<User> saveEntity2 = mongoTemplate.save(user2);

        saveEntity1.then(saveEntity2).then().as(transactionalOperator::transactional);
    }
    
}
