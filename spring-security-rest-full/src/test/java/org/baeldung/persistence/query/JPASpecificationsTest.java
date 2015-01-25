package org.baeldung.persistence.query;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.dao.UserSpecification;
import org.baeldung.persistence.model.User;
import org.baeldung.spring.PersistenceConfig;
import org.baeldung.web.util.SearchCriteria;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
@TransactionConfiguration
public class JPASpecificationsTest {

    @Autowired
    private UserRepository repository;

    private User userJohn;

    private User userTom;

    @Before
    public void init() {
        userJohn = new User();
        userJohn.setFirstName("John");
        userJohn.setLastName("Doe");
        userJohn.setEmail("john@doe.com");
        userJohn.setAge(22);
        repository.save(userJohn);

        userTom = new User();
        userTom.setFirstName("Tom");
        userTom.setLastName("Doe");
        userTom.setEmail("tom@doe.com");
        userTom.setAge(26);
        repository.save(userTom);
    }

    @Test
    public void givenLast_whenGettingListOfUsers_thenCorrect() {
        final UserSpecification spec = new UserSpecification(new SearchCriteria("lastName", ":", "doe"));
        final List<User> result = repository.findAll(spec);

        assertEquals(2, result.size());
    }

    @Test
    public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {
        final UserSpecification spec = new UserSpecification(new SearchCriteria("firstName", ":", "john"));
        final UserSpecification spec1 = new UserSpecification(new SearchCriteria("lastName", ":", "doe"));
        final List<User> result = repository.findAll(Specifications.where(spec).and(spec1));

        assertEquals(1, result.size());
        assertEquals(userJohn.getEmail(), result.get(0).getEmail());
    }

    @Test
    public void givenLastAndAge_whenGettingListOfUsers_thenCorrect() {
        final UserSpecification spec = new UserSpecification(new SearchCriteria("age", ">", "25"));
        final UserSpecification spec1 = new UserSpecification(new SearchCriteria("lastName", ":", "doe"));
        final List<User> result = repository.findAll(Specifications.where(spec).and(spec1));

        assertEquals(1, result.size());
        assertEquals(userTom.getEmail(), result.get(0).getEmail());
    }

    @Test
    public void givenWrongFirstAndLast_whenGettingListOfUsers_thenCorrect() {
        final UserSpecification spec = new UserSpecification(new SearchCriteria("firstName", ":", "Adam"));
        final UserSpecification spec1 = new UserSpecification(new SearchCriteria("lastName", ":", "Fox"));
        final List<User> result = repository.findAll(Specifications.where(spec).and(spec1));

        assertEquals(0, result.size());
    }

    @Test
    public void givenPartialFirst_whenGettingListOfUsers_thenCorrect() {
        final UserSpecification spec = new UserSpecification(new SearchCriteria("firstName", ":", "jo"));
        final List<User> result = repository.findAll(spec);

        assertEquals(1, result.size());
        assertEquals(userJohn.getEmail(), result.get(0).getEmail());
    }
}