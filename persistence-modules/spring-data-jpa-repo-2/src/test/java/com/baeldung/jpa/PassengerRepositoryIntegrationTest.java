package com.baeldung.jpa;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.IsNot.not;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.jpa.domain.Passenger;
import com.baeldung.jpa.repository.PassengerRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { JpaApplication.class})
@DirtiesContext
public class PassengerRepositoryIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private PassengerRepository repository;

    @Before
    @Transactional
    public void before() {
        entityManager.persist(Passenger.from("Jill", "Smith"));
        entityManager.persist(Passenger.from("Eve", "Jackson"));
        entityManager.persist(Passenger.from("Fred", "Bloggs"));
        entityManager.persist(Passenger.from("Ricki", "Bobbie"));
        entityManager.persist(Passenger.from("Siya", "Kolisi"));
    }

    @Transactional
    @Test
    public void givenPassengers_whenMatchingIgnoreCase_thenExpectedReturned() {
        Passenger jill = Passenger.from("Jill", "Smith");
        Passenger eve = Passenger.from("Eve", "Jackson");
        Passenger fred = Passenger.from("Fred", "Bloggs");
        Passenger siya = Passenger.from("Siya", "Kolisi");
        Passenger ricki = Passenger.from("Ricki", "Bobbie");

        List<Passenger> passengers = repository.findByFirstNameIgnoreCase("FRED");

        assertThat(passengers, contains(fred));
        assertThat(passengers, not(contains(eve)));
        assertThat(passengers, not(contains(siya)));
        assertThat(passengers, not(contains(jill)));
        assertThat(passengers, not(contains(ricki)));

    }
}
