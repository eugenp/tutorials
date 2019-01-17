package com.baeldung.mongotemplate;

import com.baeldung.config.SimpleMongoConfig;
import com.baeldung.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * 
 * This test requires:
 * * mongodb instance running on the environment
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SimpleMongoConfig.class)
public class MongoTemplateProjectionLiveTest {

    @Autowired
    private MongoTemplate mongoTemplate;

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
    public void givenUserExists_whenAgeZero_thenSuccess() {
        mongoTemplate.insert(new User("John", 30));
        mongoTemplate.insert(new User("Ringo", 35));

        final Query query = new Query();
        query.fields()
          .include("name");

        mongoTemplate.find(query, User.class)
          .forEach(user -> {
              assertNotNull(user.getName());
              assertTrue(user.getAge()
                .equals(0));
          });
    }

    @Test
    public void givenUserExists_whenIdNull_thenSuccess() {
        mongoTemplate.insert(new User("John", 30));
        mongoTemplate.insert(new User("Ringo", 35));

        final Query query = new Query();
        query.fields()
          .exclude("_id");

        mongoTemplate.find(query, User.class)
          .forEach(user -> {
              assertNull(user.getId());
              assertNotNull(user.getAge());
          });

    }

}
