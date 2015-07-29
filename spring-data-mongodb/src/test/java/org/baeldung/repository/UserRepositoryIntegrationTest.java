package org.baeldung.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.baeldung.model.User;
import org.baeldung.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/resources/mongoConfig.xml")
public class UserRepositoryIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoOperations mongoOps;

    @Before
    public void testSetup() {
        mongoOps.createCollection(User.class);
    }

    @After
    public void tearDown() {
        mongoOps.dropCollection(User.class);
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setName("Jon");
        userRepository.insertUser(user);

        assertThat(mongoOps.findOne(Query.query(Criteria.where("name").is("Jon")), User.class).getName(), is("Jon"));
    }

    @Test
    public void testSave() {
        User user = new User();
        user.setName("Jack");
        userRepository.insertUser(user);

        user = mongoOps.findOne(Query.query(Criteria.where("name").is("Jack")), User.class);
        String id = user.getId();

        user.setName("Jim");
        userRepository.saveUser(user);

        assertThat(mongoOps.findOne(Query.query(Criteria.where("id").is(id)), User.class).getName(), is("Jim"));
    }

    @Test
    public void testUpdateFirst() {
        User user = new User();
        user.setName("Alex");
        mongoOps.insert(user);

        user = new User();
        user.setName("Alex");
        mongoOps.insert(user);

        userRepository.updateFirstUser("Alex", "James");

        assertThat(mongoOps.find(Query.query(Criteria.where("name").is("James")), User.class).size(), is(1));
        assertThat(mongoOps.find(Query.query(Criteria.where("name").is("Alex")), User.class).size(), is(1));
    }

    @Test
    public void testUpdateMulti() {
        User user = new User();
        user.setName("Eugen");
        mongoOps.insert(user);

        user = new User();
        user.setName("Eugen");
        mongoOps.insert(user);

        userRepository.updateMultiUser("Eugen", "Victor");

        assertThat(mongoOps.find(Query.query(Criteria.where("name").is("Victor")), User.class).size(), is(2));
        assertThat(mongoOps.find(Query.query(Criteria.where("name").is("Eugen")), User.class).size(), is(0));
    }

    @Test
    public void testFindAndModify() {
        User user = new User();
        user.setName("Markus");
        mongoOps.insert(user);

        user = userRepository.findAndModifyUser("Markus", "Nick");

        assertThat(user.getName(), is("Markus"));
        assertThat(mongoOps.find(Query.query(Criteria.where("name").is("Nick")), User.class).size(), is(1));
        assertThat(mongoOps.find(Query.query(Criteria.where("name").is("Markus")), User.class).size(), is(0));
    }

    @Test
    public void testUpsert() {
        User user = new User();
        user.setName("Markus");
        mongoOps.insert(user);

        userRepository.upsertUser("Markus", "Nick");

        assertThat(mongoOps.find(Query.query(Criteria.where("name").is("Nick")), User.class).size(), is(1));
        assertThat(mongoOps.find(Query.query(Criteria.where("name").is("Markus")), User.class).size(), is(0));
    }

    @Test
    public void testRemove() {
        User user = new User();
        user.setName("Benn");
        mongoOps.insert(user);

        assertThat(mongoOps.find(Query.query(Criteria.where("name").is("Benn")), User.class).size(), is(1));

        userRepository.removeUser(user);

        assertThat(mongoOps.find(Query.query(Criteria.where("name").is("Benn")), User.class).size(), is(0));
    }
}
