package org.baeldung.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.baeldung.config.MongoConfig;
import org.baeldung.model.QUser;
import org.baeldung.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mysema.query.types.Predicate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = MongoConfig.class)
public class MongoRepositoryQueryIntegrationTest {

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
    public void givenUsersExist_whenFindingUsersByName_thenUsersAreFound() {
        User user = new User();
        user.setName("Jon");
        mongoOps.insert(user);

        user = new User();
        user.setName("Jon");
        mongoOps.insert(user);

        user = new User();
        user.setName("Jim");
        mongoOps.insert(user);

        List<User> users = userRepository.findUsersByName("Jon");
        assertThat(users.size(), is(2));
    }

    @Test
    public void givenUsersExist_whenFindingUsersByAgeAndReturningNames_thenUsersAreFoundAndReturnedNames() {
        User user = new User();
        user.setName("Jon");
        user.setAge(20);
        mongoOps.insert(user);

        user = new User();
        user.setName("Jon");
        user.setAge(30);
        mongoOps.insert(user);

        user = new User();
        user.setName("Jim");
        user.setAge(40);
        mongoOps.insert(user);

        List<String> users = userRepository.findUsersByAgeAndReturnNames(30);
        assertThat(users.size(), is(1));
    }

    @Test
    public void givenUsersExist_whenFindingUsersWithAgeCreaterThanAndLessThan_thenUsersAreFound() {
        User user = new User();
        user.setAge(20);
        user.setName("Jon");
        mongoOps.insert(user);

        user = new User();
        user.setAge(50);
        user.setName("Jon");
        mongoOps.insert(user);

        user = new User();
        user.setAge(33);
        user.setName("Jim");
        mongoOps.insert(user);

        List<User> users = userRepository.findByAgeBetween(26, 40);
        assertThat(users.size(), is(1));
    }

    @Test
    public void givenUsersExist_whenFindingUsersAndCount_thenUserAreFound() {
        User user = new User();
        user.setName("Alexey");
        mongoOps.insert(user);

        QUser qUser = new QUser("user");

        Predicate predicate = qUser.name.eq("Alexey");
        List<User> users = (List<User>) userRepository.findAll(predicate);

        assertThat(users.size(), is(1));
    }

}
