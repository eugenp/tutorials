package org.baeldung.persistence.query;

import org.baeldung.persistence.dao.GenericSpecificationsBuilder;
import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.persistence.dao.UserSpecification;
import org.baeldung.persistence.dao.UserSpecificationsBuilder;
import org.baeldung.persistence.model.User;
import org.baeldung.spring.PersistenceConfig;
import org.baeldung.web.util.CriteriaParser;
import org.baeldung.web.util.SearchOperation;
import org.baeldung.web.util.SpecSearchCriteria;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIn.isIn;
import static org.hamcrest.core.IsNot.not;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class })
@Transactional
@Rollback
public class JPASpecificationIntegrationTest {

    @Autowired
    private UserRepository repository;

    private User userJohn;

    private User userTom;

    private User userPercy;

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

        userPercy = new User();
        userPercy.setFirstName("percy");
        userPercy.setLastName("blackney");
        userPercy.setEmail("percy@blackney.com");
        userPercy.setAge(30);
        repository.save(userPercy);
    }

    @Test
    public void givenFirstAndLastName_whenGettingListOfUsers_thenCorrect() {
        final UserSpecification spec = new UserSpecification(new SpecSearchCriteria("firstName", SearchOperation.EQUALITY, "john"));
        final UserSpecification spec1 = new UserSpecification(new SpecSearchCriteria("lastName", SearchOperation.EQUALITY, "doe"));
        final List<User> results = repository.findAll(Specifications
          .where(spec)
          .and(spec1));

        assertThat(userJohn, isIn(results));
        assertThat(userTom, not(isIn(results)));
    }

    @Test
    public void givenFirstOrLastName_whenGettingListOfUsers_thenCorrect() {
        UserSpecificationsBuilder builder = new UserSpecificationsBuilder();

        SpecSearchCriteria spec = new SpecSearchCriteria("firstName", SearchOperation.EQUALITY, "john");
        SpecSearchCriteria spec1 = new SpecSearchCriteria("'","lastName", SearchOperation.EQUALITY, "doe");

        List<User> results = repository.findAll(builder
          .with(spec)
          .with(spec1)
          .build());

        assertThat(results, hasSize(2));
        assertThat(userJohn, isIn(results));
        assertThat(userTom, isIn(results));
    }

    @Test
    public void givenFirstOrLastNameAndAgeGenericBuilder_whenGettingListOfUsers_thenCorrect() {
        GenericSpecificationsBuilder<User> builder = new GenericSpecificationsBuilder<>();
        Function<SpecSearchCriteria, Specification<User>> converter = UserSpecification::new;
        
        CriteriaParser parser=new CriteriaParser();
        List<User> results = repository.findAll(builder.build(parser.parse("( lastName:doe OR firstName:john ) AND age:22"), converter));

        assertThat(results, hasSize(1));
        assertThat(userJohn, isIn(results));
        assertThat(userTom, not(isIn(results)));
    }

    @Test
    public void givenFirstOrLastNameGenericBuilder_whenGettingListOfUsers_thenCorrect() {
        GenericSpecificationsBuilder<User> builder = new GenericSpecificationsBuilder<>();
        Function<SpecSearchCriteria, Specification<User>> converter = UserSpecification::new;
        
        builder.with("firstName", ":", "john", null, null);
        builder.with("'", "lastName", ":", "doe", null, null);

        List<User> results = repository.findAll(builder.build(converter));

        assertThat(results, hasSize(2));
        assertThat(userJohn, isIn(results));
        assertThat(userTom, isIn(results));
    }

    @Test
    public void givenFirstNameInverse_whenGettingListOfUsers_thenCorrect() {
        final UserSpecification spec = new UserSpecification(new SpecSearchCriteria("firstName", SearchOperation.NEGATION, "john"));
        final List<User> results = repository.findAll(Specifications.where(spec));

        assertThat(userTom, isIn(results));
        assertThat(userJohn, not(isIn(results)));
    }

    @Test
    public void givenMinAge_whenGettingListOfUsers_thenCorrect() {
        final UserSpecification spec = new UserSpecification(new SpecSearchCriteria("age", SearchOperation.GREATER_THAN, "25"));
        final List<User> results = repository.findAll(Specifications.where(spec));
        assertThat(userTom, isIn(results));
        assertThat(userJohn, not(isIn(results)));
    }

    @Test
    public void givenFirstNamePrefix_whenGettingListOfUsers_thenCorrect() {
        final UserSpecification spec = new UserSpecification(new SpecSearchCriteria("firstName", SearchOperation.STARTS_WITH, "jo"));
        final List<User> results = repository.findAll(spec);
        assertThat(userJohn, isIn(results));
        assertThat(userTom, not(isIn(results)));
    }

    @Test
    public void givenFirstNameSuffix_whenGettingListOfUsers_thenCorrect() {
        final UserSpecification spec = new UserSpecification(new SpecSearchCriteria("firstName", SearchOperation.ENDS_WITH, "n"));
        final List<User> results = repository.findAll(spec);
        assertThat(userJohn, isIn(results));
        assertThat(userTom, not(isIn(results)));
    }

    @Test
    public void givenFirstNameSubstring_whenGettingListOfUsers_thenCorrect() {
        final UserSpecification spec = new UserSpecification(new SpecSearchCriteria("firstName", SearchOperation.CONTAINS, "oh"));
        final List<User> results = repository.findAll(spec);

        assertThat(userJohn, isIn(results));
        assertThat(userTom, not(isIn(results)));
    }

    @Test
    public void givenAgeRange_whenGettingListOfUsers_thenCorrect() {
        final UserSpecification spec = new UserSpecification(new SpecSearchCriteria("age", SearchOperation.GREATER_THAN, "20"));
        final UserSpecification spec1 = new UserSpecification(new SpecSearchCriteria("age", SearchOperation.LESS_THAN, "25"));
        final List<User> results = repository.findAll(Specifications
          .where(spec)
          .and(spec1));

        assertThat(userJohn, isIn(results));
        assertThat(userTom, not(isIn(results)));
    }
}
