package com.baeldung.transaction;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.SessionSynchronization;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baeldung.config.MongoConfig;
import com.baeldung.model.User;

/**
 * 
 * This test requires:
 * * mongodb instance running on the environment
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoConfig.class)
public class MongoTransactionTemplateLiveTest {

    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private MongoTransactionManager mongoTransactionManager;

    @Before
    public void testSetup() {
        if (!mongoTemplate.collectionExists(User.class)) {
            mongoTemplate.createCollection(User.class);
        }
    }

    @After
    public void tearDown() {
        mongoTemplate.dropCollection(User.class);
    }

    @Test
    public void givenTransactionTemplate_whenPerformTransaction_thenSuccess() {
        mongoTemplate.setSessionSynchronization(SessionSynchronization.ALWAYS);                                     
        TransactionTemplate transactionTemplate = new TransactionTemplate(mongoTransactionManager);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                mongoTemplate.insert(new User("Kim", 20));
                mongoTemplate.insert(new User("Jack", 45));
            };
        });

        Query query = new Query().addCriteria(Criteria.where("name")
            .is("Jack"));
        List<User> users = mongoTemplate.find(query, User.class);
        
        assertThat(users.size(), is(1));
    }
   
}
