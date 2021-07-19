package com.baeldung.mongotemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

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
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexInfo;
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
public class MongoTemplateQueryLiveTest {

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
    public void givenUsersExist_whenFindingUserWithAgeLessThan50AndGreateThan20_thenUsersAreFound() {
        User user = new User();
        user.setName("Eric");
        user.setAge(45);
        mongoTemplate.insert(user);
        user = new User();
        user.setName("Antony");
        user.setAge(55);
        mongoTemplate.insert(user);

        Query query = new Query();
        query.addCriteria(Criteria.where("age").lt(50).gt(20));
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
        assertThat(users.size(), is(3));
    }

    @Test
    public void givenUserExistsWithIndexAddedFromMapping_whenCheckingIndex_thenIndexIsExisted() {
        final User user = new User();
        user.setName("Brendan");
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setValue("a@gmail.com");
        user.setEmailAddress(emailAddress);
        mongoTemplate.insert(user);

        List<IndexInfo> indexInfos = mongoTemplate.indexOps("user").getIndexInfo();

        assertThat(indexInfos.size(), is(1));
    }

    @Test
    public void whenSavingUserWithEmailAddress_thenUserandEmailAddressSaved() {
        final User user = new User();
        user.setName("Brendan");
        final EmailAddress emailAddress = new EmailAddress();
        emailAddress.setValue("b@gmail.com");
        user.setEmailAddress(emailAddress);
        mongoTemplate.insert(user);

        assertThat(mongoTemplate.findOne(Query.query(Criteria.where("name").is("Brendan")), User.class).getEmailAddress().getValue(), is("b@gmail.com"));
    }

    @Test
    public void givenUserExistsWithIndexAddedFromCode_whenCheckingIndex_thenIndexIsExisted() {
        final User user = new User();
        user.setName("Brendan");
        mongoTemplate.indexOps(User.class).ensureIndex(new Index().on("name", Direction.ASC));
        mongoTemplate.insert(user);

        List<IndexInfo> indexInfos = mongoTemplate.indexOps("user").getIndexInfo();

        assertThat(indexInfos.size(), is(2));
    }

    @Test
    public void whenSavingUserWithoutSettingAge_thenAgeIsSetByDefault() {
        final User user = new User();
        user.setName("Alex");
        mongoTemplate.insert(user);

        assertThat(mongoTemplate.findOne(Query.query(Criteria.where("name").is("Alex")), User.class).getAge(), is(0));
    }

    @Test
    public void whenSavingUser_thenYearOfBirthIsCalculated() {
        final User user = new User();
        user.setName("Alex");
        user.setYearOfBirth(1985);
        mongoTemplate.insert(user);

        assertThat(mongoTemplate.findOne(Query.query(Criteria.where("name").is("Alex")), User.class).getYearOfBirth(), is(nullValue()));
    }
}
