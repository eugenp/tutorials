package com.baeldung.persistence.query;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIn.isIn;
import static org.hamcrest.core.IsNot.not;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.persistence.dao.UserRepository;
import com.baeldung.persistence.dao.rsql.CustomRsqlVisitor;
import com.baeldung.persistence.model.User;
import com.baeldung.spring.PersistenceConfig;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class })
@Transactional
@Rollback
public class RsqlIntegrationTest {

    @Autowired
    private UserRepository repository;

    private User userJohn;

    private User userTom;

    @Before
    public void init() {
        userJohn = new User();
        userJohn.setFirstName("john");
        userJohn.setLastName("doe");
        userJohn.setEmail("john@doe.com");
        userJohn.setAge(22);
        repository.save(userJohn);

        userTom = new User();
        userTom.setFirstName("tom");
        userTom.setLastName("doe");
        userTom.setEmail("tom@doe.com");
        userTom.setAge(26);
        repository.save(userTom);
    }

    @Test
    public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {
        final Node rootNode = new RSQLParser().parse("firstName==john;lastName==doe");
        final Specification<User> spec = rootNode.accept(new CustomRsqlVisitor<User>());
        final List<User> results = repository.findAll(spec);

        assertThat(userJohn, isIn(results));
        assertThat(userTom, not(isIn(results)));
    }

    @Test
    public void givenFirstNameInverse_whenGettingListOfUsers_thenCorrect() {
        final Node rootNode = new RSQLParser().parse("firstName!=john");
        final Specification<User> spec = rootNode.accept(new CustomRsqlVisitor<User>());
        final List<User> results = repository.findAll(spec);

        assertThat(userTom, isIn(results));
        assertThat(userJohn, not(isIn(results)));
    }

    @Test
    public void givenMinAge_whenGettingListOfUsers_thenCorrect() {
        final Node rootNode = new RSQLParser().parse("age>25");
        final Specification<User> spec = rootNode.accept(new CustomRsqlVisitor<User>());
        final List<User> results = repository.findAll(spec);

        assertThat(userTom, isIn(results));
        assertThat(userJohn, not(isIn(results)));
    }

    @Test
    public void givenFirstNamePrefix_whenGettingListOfUsers_thenCorrect() {
        final Node rootNode = new RSQLParser().parse("firstName==jo*");
        final Specification<User> spec = rootNode.accept(new CustomRsqlVisitor<User>());
        final List<User> results = repository.findAll(spec);

        assertThat(userJohn, isIn(results));
        assertThat(userTom, not(isIn(results)));
    }

    @Test
    public void givenListOfFirstName_whenGettingListOfUsers_thenCorrect() {
        final Node rootNode = new RSQLParser().parse("firstName=in=(john,jack)");
        final Specification<User> spec = rootNode.accept(new CustomRsqlVisitor<User>());
        final List<User> results = repository.findAll(spec);

        assertThat(userJohn, isIn(results));
        assertThat(userTom, not(isIn(results)));
    }
}
