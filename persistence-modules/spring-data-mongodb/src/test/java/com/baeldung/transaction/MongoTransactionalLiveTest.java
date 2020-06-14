package com.baeldung.transaction;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoTransactionException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.config.MongoConfig;
import com.baeldung.model.User;
import com.baeldung.repository.UserRepository;
import com.mongodb.MongoCommandException;

/**
 * 
 * This test requires:
 * * mongodb instance running on the environment
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoConfig.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MongoTransactionalLiveTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void whenPerformMongoTransaction_thenSuccess() {
        userRepository.save(new User("John", 30));
        userRepository.save(new User("Ringo", 35));
        Query query = new Query().addCriteria(Criteria.where("name")
            .is("John"));
        List<User> users = mongoTemplate.find(query, User.class);

        assertThat(users.size(), is(1));
    }

    @Test(expected = MongoTransactionException.class)
    @Transactional
    public void whenListCollectionDuringMongoTransaction_thenException() {
        if (mongoTemplate.collectionExists(User.class)) {
            mongoTemplate.save(new User("John", 30));
            mongoTemplate.save(new User("Ringo", 35));
        }
    }

    // ==== Using test instead of before and after due to @transactional doesn't allow list collection

    @Test
    public void setup() {
        if (!mongoTemplate.collectionExists(User.class)) {
            mongoTemplate.createCollection(User.class);
        }
    }

    @Test
    public void ztearDown() {
        mongoTemplate.dropCollection(User.class);
    }
}
