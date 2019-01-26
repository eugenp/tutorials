package com.baeldung.passenger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;

@DataJpaTest
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
        assertEquals(actual, expected);
    }

    @Test
    public void givenSeveralPassengersWhenFindFirstByOrderBySeatNumberAscThenThePassengerInTheFirstFilledSeatIsReturned() {
        Passenger expected = Passenger.from("Fred", "Bloggs", 22);

        Passenger actual = repository.findFirstByOrderBySeatNumberAsc();

        assertEquals(actual, expected);
    }

    @Test
    public void givenSeveralPassengersWhenFindPageSortedByThenThePassengerInTheFirstFilledSeatIsReturned() {
        Passenger expected = Passenger.from("Fred", "Bloggs", 22);

        Page<Passenger> page = repository.findAll(PageRequest.of(0, 1, Sort.by(Sort.Direction.ASC, "seatNumber")));

        assertEquals(page.getContent().size(), 1);

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
    
}
