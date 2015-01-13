package org.baeldung.persistence.query;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.baeldung.config.PersistenceJPAConfig;
import org.baeldung.persistence.model.User;
import org.baeldung.persistence.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceJPAConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
@TransactionConfiguration
public class JPACriteriaQueryTest {

    @Autowired
    private UserService userService;

    private User user_john;

    private User user_tom;

    @Before
    public void init() {
        user_john = new User();
        user_john.setFirstName("John");
        user_john.setLastName("Doe");
        user_john.setEmail("john@doe.com");
        user_john.setAge(22);
        userService.saveUser(user_john);

        user_tom = new User();
        user_tom.setFirstName("Tom");
        user_tom.setLastName("Doe");
        user_tom.setEmail("tom@doe.com");
        user_tom.setAge(26);
        userService.saveUser(user_tom);
    }

    @Test
    public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {
        final List<User> result = userService.searchUser("John", "Doe", 0);

        assertEquals(1, result.size());
        assertEquals(user_john.getEmail(), result.get(0).getEmail());
    }

    @Test
    public void givenLast_whenGettingListOfUsers_thenCorrect() {
        final List<User> result = userService.searchUser("", "doe", 0);
        assertEquals(2, result.size());
    }

    @Test
    public void givenLastAndAge_whenGettingListOfUsers_thenCorrect() {
        final List<User> result = userService.searchUser("", "doe", 25);

        assertEquals(1, result.size());
        assertEquals(user_tom.getEmail(), result.get(0).getEmail());
    }

    @Test
    public void givenWrongFirstAndLast_whenGettingListOfUsers_thenCorrect() {
        final List<User> result = userService.searchUser("Adam", "Fox", 0);
        assertEquals(0, result.size());
    }

    @Test
    public void givenPartialFirstAndLast_whenGettingListOfUsers_thenCorrect() {
        final List<User> result = userService.searchUser("jo", "", 0);

        assertEquals(1, result.size());
        assertEquals(user_john.getEmail(), result.get(0).getEmail());
    }
}
