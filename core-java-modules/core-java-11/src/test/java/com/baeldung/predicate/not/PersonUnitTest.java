package com.baeldung.predicate.not;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PersonUnitTest {
    private List<Person> people;

    @BeforeEach
    void preparePeople() {
        people = Arrays.asList(
          new Person(1),
          new Person(18),
          new Person(2)
        );
    }

    @Test
    void givenPeople_whenFilterIsAdult_thenOneResult() {
        List<Person> adults = people.stream()
          .filter(Person::isAdult)
          .collect(Collectors.toList());

        assertThat(adults).size().isEqualTo(1);
    }

    @Test
    void givenPeople_whenFilterIsAdultNegated_thenTwoResults() {
        List<Person> nonAdults = people.stream()
          .filter(person -> !person.isAdult())
          .collect(Collectors.toList());

        assertThat(nonAdults).size().isEqualTo(2);
    }

    @Test
    void givenPeople_whenFilterIsNotAdult_thenTwoResults() {
        List<Person> nonAdults = people.stream()
          .filter(Person::isNotAdult)
          .collect(Collectors.toList());

        assertThat(nonAdults).size().isEqualTo(2);
    }

    @Test
    void givenPeople_whenFilterNotIsAdult_thenTwoResults() {
        List<Person> nonAdults = people.stream()
          .filter(not(Person::isAdult))
          .collect(Collectors.toList());

        assertThat(nonAdults).size().isEqualTo(2);
    }
}