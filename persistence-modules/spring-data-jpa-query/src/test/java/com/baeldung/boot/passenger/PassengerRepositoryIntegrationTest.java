package com.baeldung.boot.passenger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.boot.passenger.Passenger;
import com.baeldung.boot.passenger.PassengerRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@DataJpaTest(showSql = false)
@RunWith(SpringRunner.class)
public class PassengerRepositoryIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private PassengerRepository repository;

    @Before
    public void before() {
        entityManager.persist(Passenger.from("Jill", "Smith", 50));
        entityManager.persist(Passenger.from("Eve", "Jackson", 95));
        entityManager.persist(Passenger.from("Fred", "Bloggs", 22));
        entityManager.persist(Passenger.from("Ricki", "Bobbie", 36));
        entityManager.persist(Passenger.from("Siya", "Kolisi", 85));
    }

    @Test
    public void givenSeveralPassengersWhenOrderedBySeatNumberLimitedToThenThePassengerInTheFirstFilledSeatIsReturned() {
        Passenger expected = Passenger.from("Fred", "Bloggs", 22);

        List<Passenger> passengers = repository.findOrderedBySeatNumberLimitedTo(1);

        assertEquals(1, passengers.size());

        Passenger actual = passengers.get(0);
        assertEquals(expected, actual);
    }

    @Test
    public void givenSeveralPassengersWhenFindFirstByOrderBySeatNumberAscThenThePassengerInTheFirstFilledSeatIsReturned() {
        Passenger expected = Passenger.from("Fred", "Bloggs", 22);

        Passenger actual = repository.findFirstByOrderBySeatNumberAsc();

        assertEquals(expected, actual);
    }

    @Test
    public void givenSeveralPassengersWhenFindPageSortedByThenThePassengerInTheFirstFilledSeatIsReturned() {
        Passenger expected = Passenger.from("Fred", "Bloggs", 22);

        Page<Passenger> page = repository.findAll(PageRequest.of(0, 1,
          Sort.by(Sort.Direction.ASC, "seatNumber")));

        assertEquals(1, page.getContent().size());

        Passenger actual = page.getContent().get(0);
        assertEquals(expected, actual);
    }

    @Test
    public void givenPassengers_whenOrderedBySeatNumberAsc_thenCorrectOrder() {
        Passenger fred = Passenger.from("Fred", "Bloggs", 22);
        Passenger ricki = Passenger.from("Ricki", "Bobbie", 36);
        Passenger jill = Passenger.from("Jill", "Smith", 50);
        Passenger siya = Passenger.from("Siya", "Kolisi", 85);
        Passenger eve = Passenger.from("Eve", "Jackson", 95);

        List<Passenger> passengers = repository.findByOrderBySeatNumberAsc();

        assertThat(passengers, contains(fred, ricki, jill, siya, eve));
    }

    @Test
    public void givenPassengers_whenFindAllWithSortBySeatNumberAsc_thenCorrectOrder() {
        Passenger fred = Passenger.from("Fred", "Bloggs", 22);
        Passenger ricki = Passenger.from("Ricki", "Bobbie", 36);
        Passenger jill = Passenger.from("Jill", "Smith", 50);
        Passenger siya = Passenger.from("Siya", "Kolisi", 85);
        Passenger eve = Passenger.from("Eve", "Jackson", 95);

        List<Passenger> passengers = repository.findAll(Sort.by(Sort.Direction.ASC, "seatNumber"));

        assertThat(passengers, contains(fred, ricki, jill, siya, eve));
    }

    @Test
    public void givenPassengers_whenFindByExampleDefaultMatcher_thenExpectedReturned() {
        Example<Passenger> example = Example.of(Passenger.from("Fred", "Bloggs", null));

        Optional<Passenger> actual = repository.findOne(example);

        assertTrue(actual.isPresent());
        assertEquals(Passenger.from("Fred", "Bloggs", 22), actual.get());
    }

    @Test
    public void givenPassengers_whenFindByExampleCaseInsensitiveMatcher_thenExpectedReturned() {
        ExampleMatcher caseInsensitiveExampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase();
        Example<Passenger> example = Example.of(Passenger.from("fred", "bloggs", null),
          caseInsensitiveExampleMatcher);

        Optional<Passenger> actual = repository.findOne(example);

        assertTrue(actual.isPresent());
        assertEquals(Passenger.from("Fred", "Bloggs", 22), actual.get());
    }

    @Test
    public void givenPassengers_whenFindByExampleCustomMatcher_thenExpectedReturned() {
        Passenger jill = Passenger.from("Jill", "Smith", 50);
        Passenger eve = Passenger.from("Eve", "Jackson", 95);
        Passenger fred = Passenger.from("Fred", "Bloggs", 22);
        Passenger siya = Passenger.from("Siya", "Kolisi", 85);
        Passenger ricki = Passenger.from("Ricki", "Bobbie", 36);

        ExampleMatcher customExampleMatcher = ExampleMatcher.matchingAny().withMatcher("firstName",
          ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase()).withMatcher("lastName",
          ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

        Example<Passenger> example = Example.of(Passenger.from("e", "s", null),
          customExampleMatcher);

        List<Passenger> passengers = repository.findAll(example);

        assertThat(passengers, contains(jill, eve, fred, siya));
        assertThat(passengers, not(contains(ricki)));
    }

    @Test
    public void givenPassengers_whenFindByIgnoringMatcher_thenExpectedReturned() {
        Passenger jill = Passenger.from("Jill", "Smith", 50);
        Passenger eve = Passenger.from("Eve", "Jackson", 95);
        Passenger fred = Passenger.from("Fred", "Bloggs", 22);
        Passenger siya = Passenger.from("Siya", "Kolisi", 85);
        Passenger ricki = Passenger.from("Ricki", "Bobbie", 36);

        ExampleMatcher ignoringExampleMatcher = ExampleMatcher.matchingAny().withMatcher("lastName",
            ExampleMatcher.GenericPropertyMatchers.startsWith().ignoreCase()).withIgnorePaths("firstName", "seatNumber");

        Example<Passenger> example = Example.of(Passenger.from(null, "b", null),
            ignoringExampleMatcher);

        List<Passenger> passengers = repository.findAll(example);

        assertThat(passengers, contains(fred, ricki));
        assertThat(passengers, not(contains(jill)));
        assertThat(passengers, not(contains(eve)));
        assertThat(passengers, not(contains(siya)));
    }

    @Test
    public void givenPassengers_whenMatchingIgnoreCase_thenExpectedReturned() {
        Passenger jill = Passenger.from("Jill", "Smith", 50);
        Passenger eve = Passenger.from("Eve", "Jackson", 95);
        Passenger fred = Passenger.from("Fred", "Bloggs", 22);
        Passenger siya = Passenger.from("Siya", "Kolisi", 85);
        Passenger ricki = Passenger.from("Ricki", "Bobbie", 36);

        List<Passenger> passengers = repository.findByFirstNameIgnoreCase("FRED");

        assertThat(passengers, contains(fred));
        assertThat(passengers, not(contains(eve)));
        assertThat(passengers, not(contains(siya)));
        assertThat(passengers, not(contains(jill)));
        assertThat(passengers, not(contains(ricki)));

    }
}
