package org.baeldung.mongotemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.baeldung.config.MongoConfig;
import org.baeldung.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoConfig.class)
public class MongoTemplateIntegrationTest {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void testSetup() {
        mongoTemplate.createCollection(User.class);
    }

    @After
    public void tearDown() {
        mongoTemplate.dropCollection(User.class);
    }
    
    @Test
    public void whenInsertingUser_thenUserIsInserted() {
        User user = new User();
        user.setName("Jon");
        mongoTemplate.insert(user);
    }

    @Test
    public void givenUserExists_whenSavingExistUser_thenUserIsUpdated() {
        User user = new User();
        user.setName("Jack");
        mongoTemplate.insert(user);
        
        user = new User();
        user.setName("Jim");
        mongoTemplate.save(user);
    }

    @Test
    public void givenUsersExist_whenUpdatingFirstUser_thenFirstUserIsUpdated() {
        User user = new User();
        user.setName("Alex");
        mongoTemplate.insert(user);

        user = new User();
        user.setName("Alex");
        mongoTemplate.insert(user);
        
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Alex"));
        Update update = new Update();
        update.set("name", "James");
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Test
    public void givenUsersExist_whenUpdatingMultiUser_thenAllUserAreUpdated() {
        User user = new User();
        user.setName("Eugen");
        mongoTemplate.insert(user);

        user = new User();
        user.setName("Eugen");
        mongoTemplate.insert(user);
        
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Eugen"));
        Update update = new Update();
        update.set("name", "Victor");
        mongoTemplate.updateMulti(query, update, User.class);
    }

    @Test
    public void givenUserExists_whenFindingAndModifyingUser_thenUserIsReturnedAndUpdated() {
        User user = new User();
        user.setName("Markus");
        mongoTemplate.insert(user);
        
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Markus"));
        Update update = new Update();
        update.set("name", "Nick");
        user = mongoTemplate.findAndModify(query, update, User.class);
        assertThat(user.getName(),is("Markus"));
    }

    @Test
    public void givenUserExists_whenUpsertingUser_thenUserIsUpdated() {
        User user = new User();
        user.setName("Markus");
        mongoTemplate.insert(user);
        
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Markus"));
        Update update = new Update();
        update.set("name", "Nick");
        mongoTemplate.upsert(query, update, User.class);
    }

    @Test
    public void givenUserExists_whenRemovingUser_thenUserIsRemoved() {
        User user = new User();
        user.setName("Benn");
        mongoTemplate.insert(user);
        
        mongoTemplate.remove(user, "user");
    }

}
