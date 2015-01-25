package org.baeldung.persistence.query;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.baeldung.persistence.model.User;
import org.baeldung.persistence.service.impl.UserService;
import org.baeldung.spring.PersistenceConfig;
import org.baeldung.web.util.SearchCriteria;
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
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
@TransactionConfiguration
public class JPACriteriaQueryTest {

    @Autowired
    private UserService userService;

    private User userJohn;

    private User userTom;

    @Before
    public void init() {
        userJohn = new User();
        userJohn.setFirstName("John");
        userJohn.setLastName("Doe");
        userJohn.setEmail("john@doe.com");
        userJohn.setAge(22);
        userService.saveUser(userJohn);

        userTom = new User();
        userTom.setFirstName("Tom");
        userTom.setLastName("Doe");
        userTom.setEmail("tom@doe.com");
        userTom.setAge(26);
        userService.saveUser(userTom);
    }

    @Test
    public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {
        final List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        params.add(new SearchCriteria("firstName", ":", "John"));
        params.add(new SearchCriteria("lastName", ":", "Doe"));

        final List<User> result = userService.searchUser(params);

        assertEquals(1, result.size());
        assertEquals(userJohn.getEmail(), result.get(0).getEmail());
    }

    @Test
    public void givenLast_whenGettingListOfUsers_thenCorrect() {
        final List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        params.add(new SearchCriteria("lastName", ":", "Doe"));

        final List<User> result = userService.searchUser(params);
        assertEquals(2, result.size());
    }

    @Test
    public void givenLastAndAge_whenGettingListOfUsers_thenCorrect() {
        final List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        params.add(new SearchCriteria("lastName", ":", "Doe"));
        params.add(new SearchCriteria("age", ">", "25"));

        final List<User> result = userService.searchUser(params);

        assertEquals(1, result.size());
        assertEquals(userTom.getEmail(), result.get(0).getEmail());
    }

    @Test
    public void givenWrongFirstAndLast_whenGettingListOfUsers_thenCorrect() {
        final List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        params.add(new SearchCriteria("firstName", ":", "Adam"));
        params.add(new SearchCriteria("lastName", ":", "Fox"));

        final List<User> result = userService.searchUser(params);
        assertEquals(0, result.size());
    }

    @Test
    public void givenPartialFirst_whenGettingListOfUsers_thenCorrect() {
        final List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        params.add(new SearchCriteria("firstName", ":", "jo"));

        final List<User> result = userService.searchUser(params);

        assertEquals(1, result.size());
        assertEquals(userJohn.getEmail(), result.get(0).getEmail());
    }
}