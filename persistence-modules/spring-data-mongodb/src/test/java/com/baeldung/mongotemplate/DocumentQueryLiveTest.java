package com.baeldung.mongotemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.config.MongoConfig;
import com.baeldung.model.EmailAddress;
import com.baeldung.model.User;

/**
 * 
 * This test requires:
 * * mongodb instance running on the environment
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoConfig.class)
public class DocumentQueryLiveTest {

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
        mongoTemplate.dropCollection(EmailAddress.class);
        mongoTemplate.dropCollection(User.class);
    }

    @Test
    public void givenUsersExist_whenFindingUsersByName_thenUsersAreFound() {
        User user = new User();
        user.setName("Eric");
        user.setAge(45);
        mongoTemplate.insert(user);
        user = new User();
        user.setName("Antony");
        user.setAge(55);
        mongoTemplate.insert(user);

        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Eric"));
        List<User> users = mongoTemplate.find(query, User.class);

        assertThat(users.size(), is(1));
    }

    @Test
    public void givenUsersExist_whenFindingUserWithAgeLessThan50AndGreateThan20_thenUsersAreFound() {
        User user = new User();
        user.setAge(20);
        user.setName("Jon");
        mongoTemplate.insert(user);

        user = new User();
        user.setAge(50);
        user.setName("Jon");
        mongoTemplate.insert(user);

        user = new User();
        user.setAge(33);
        user.setName("Jim");
        mongoTemplate.insert(user);

        Query query = new Query();
        query.addCriteria(Criteria.where("age").lt(40).gt(26));
        List<User> users = mongoTemplate.find(query, User.class);

        assertThat(users.size(), is(1));
    }

    @Test
    public void givenUsersExist_whenFindingUserWithNameStartWithA_thenUsersAreFound() {
        User user = new User();
        user.setName("Eric");
        user.setAge(45);
        mongoTemplate.insert(user);

        user = new User();
        user.setName("Antony");
        user.setAge(33);
        mongoTemplate.insert(user);

        user = new User();
        user.setName("Alice");
        user.setAge(35);
        mongoTemplate.insert(user);

        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("^A"));

        List<User> users = mongoTemplate.find(query, User.class);

        assertThat(users.size(), is(2));
    }

    @Test
    public void givenUsersExist_whenFindingUserWithNameEndWithC_thenUsersAreFound() {
        User user = new User();
        user.setName("Eric");
        user.setAge(45);
        mongoTemplate.insert(user);

        user = new User();
        user.setName("Antony");
        user.setAge(33);
        mongoTemplate.insert(user);

        user = new User();
        user.setName("Alice");
        user.setAge(35);
        mongoTemplate.insert(user);

        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex("c$"));

        List<User> users = mongoTemplate.find(query, User.class);

        assertThat(users.size(), is(1));
    }

    @Test
    public void givenUsersExist_whenFindingByPage_thenUsersAreFoundByPage() {
        User user = new User();
        user.setName("Eric");
        user.setAge(45);
        mongoTemplate.insert(user);

        user = new User();
        user.setName("Antony");
        user.setAge(33);
        mongoTemplate.insert(user);

        user = new User();
        user.setName("Alice");
        user.setAge(35);
        mongoTemplate.insert(user);

        final Pageable pageableRequest = PageRequest.of(0, 2);
        Query query = new Query();
        query.with(pageableRequest);

        List<User> users = mongoTemplate.find(query, User.class);

        assertThat(users.size(), is(2));
    }

    @Test
    public void givenUsersExist_whenFindingUsersAndSortThem_thenUsersAreFoundAndSorted() {
        User user = new User();
        user.setName("Eric");
        user.setAge(45);
        mongoTemplate.insert(user);

        user = new User();
        user.setName("Antony");
        user.setAge(33);
        mongoTemplate.insert(user);

        user = new User();
        user.setName("Alice");
        user.setAge(35);
        mongoTemplate.insert(user);

        Query query = new Query();
        query.with(Sort.by(Direction.ASC, "age"));

        List<User> users = mongoTemplate.find(query, User.class);

        Iterator<User> iter = users.iterator();
        assertThat(users.size(), is(3));
        assertThat(iter.next().getName(), is("Antony"));
        assertThat(iter.next().getName(), is("Alice"));
        assertThat(iter.next().getName(), is("Eric"));
    }
}
