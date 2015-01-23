package org.baeldung.persistence.query;

import static org.baeldung.persistence.dao.MyUserPrdicates.ageIsGreaterThan;
import static org.baeldung.persistence.dao.MyUserPrdicates.firstNameIsLike;
import static org.baeldung.persistence.dao.MyUserPrdicates.lastNameIsLike;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyIterable.emptyIterable;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.IsNot.not;

import org.baeldung.persistence.dao.MyUserRepository;
import org.baeldung.persistence.model.MyUser;
import org.baeldung.spring.PersistenceConfig;
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
public class JPAQuerydslTest {

    @Autowired
    private MyUserRepository repository;

    private MyUser userJohn;

    private MyUser userTom;

    @Before
    public void init() {
        userJohn = new MyUser();
        userJohn.setFirstName("John");
        userJohn.setLastName("Doe");
        userJohn.setEmail("john@doe.com");
        userJohn.setAge(22);
        repository.save(userJohn);

        userTom = new MyUser();
        userTom.setFirstName("Tom");
        userTom.setLastName("Doe");
        userTom.setEmail("tom@doe.com");
        userTom.setAge(26);
        repository.save(userTom);
    }

    @Test
    public void givenLast_whenGettingListOfUsers_thenCorrect() {
        final Iterable<MyUser> result = repository.findAll(lastNameIsLike("doe"));
        assertThat(result, containsInAnyOrder(userJohn, userTom));
    }

    @Test
    public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {
        final Iterable<MyUser> result = repository.findAll(lastNameIsLike("doe").and(firstNameIsLike("john")));

        assertThat(result, contains(userJohn));
        assertThat(result, not(contains(userTom)));
    }

    @Test
    public void givenLastAndAge_whenGettingListOfUsers_thenCorrect() {
        final Iterable<MyUser> result = repository.findAll(lastNameIsLike("doe").and(ageIsGreaterThan(25)));

        assertThat(result, contains(userTom));
        assertThat(result, not(contains(userJohn)));
    }

    @Test
    public void givenWrongFirstAndLast_whenGettingListOfUsers_thenCorrect() {
        final Iterable<MyUser> result = repository.findAll(lastNameIsLike("Adam").and(firstNameIsLike("Fox")));
        assertThat(result, emptyIterable());
    }

    @Test
    public void givenPartialFirst_whenGettingListOfUsers_thenCorrect() {
        final Iterable<MyUser> result = repository.findAll(firstNameIsLike("jo"));

        assertThat(result, contains(userJohn));
        assertThat(result, not(contains(userTom)));
    }
}
