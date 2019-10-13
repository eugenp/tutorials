package com.baeldung.persistence.query;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyIterable.emptyIterable;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.IsNot.not;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.persistence.dao.MyUserPredicatesBuilder;
import com.baeldung.persistence.dao.MyUserRepository;
import com.baeldung.persistence.model.MyUser;
import com.baeldung.spring.PersistenceConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class })
@Transactional
@Rollback
public class JPAQuerydslIntegrationTest {

    @Autowired
    private MyUserRepository repo;

    private MyUser userJohn;

    private MyUser userTom;

    @Before
    public void init() {
        userJohn = new MyUser();
        userJohn.setFirstName("john");
        userJohn.setLastName("doe");
        userJohn.setEmail("john@doe.com");
        userJohn.setAge(22);
        repo.save(userJohn);

        userTom = new MyUser();
        userTom.setFirstName("tom");
        userTom.setLastName("doe");
        userTom.setEmail("tom@doe.com");
        userTom.setAge(26);
        repo.save(userTom);
    }

    @Test
    public void givenLast_whenGettingListOfUsers_thenCorrect() {
        final MyUserPredicatesBuilder builder = new MyUserPredicatesBuilder().with("lastName", ":", "doe");

        final Iterable<MyUser> results = repo.findAll(builder.build());
        assertThat(results, containsInAnyOrder(userJohn, userTom));
    }

    @Test
    public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {
        final MyUserPredicatesBuilder builder = new MyUserPredicatesBuilder().with("firstName", ":", "john").with("lastName", ":", "doe");

        final Iterable<MyUser> results = repo.findAll(builder.build());

        assertThat(results, contains(userJohn));
        assertThat(results, not(contains(userTom)));
    }

    @Test
    public void givenLastAndAge_whenGettingListOfUsers_thenCorrect() {
        final MyUserPredicatesBuilder builder = new MyUserPredicatesBuilder().with("lastName", ":", "doe").with("age", ">", "25");

        final Iterable<MyUser> results = repo.findAll(builder.build());

        assertThat(results, contains(userTom));
        assertThat(results, not(contains(userJohn)));
    }

    @Test
    public void givenWrongFirstAndLast_whenGettingListOfUsers_thenCorrect() {
        final MyUserPredicatesBuilder builder = new MyUserPredicatesBuilder().with("firstName", ":", "adam").with("lastName", ":", "fox");

        final Iterable<MyUser> results = repo.findAll(builder.build());
        assertThat(results, emptyIterable());
    }

    @Test
    public void givenPartialFirst_whenGettingListOfUsers_thenCorrect() {
        final MyUserPredicatesBuilder builder = new MyUserPredicatesBuilder().with("firstName", ":", "jo");

        final Iterable<MyUser> results = repo.findAll(builder.build());

        assertThat(results, contains(userJohn));
        assertThat(results, not(contains(userTom)));
    }
}