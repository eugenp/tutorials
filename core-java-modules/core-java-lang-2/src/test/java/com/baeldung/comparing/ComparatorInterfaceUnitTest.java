package com.baeldung.comparing;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class ComparatorInterfaceUnitTest {

    @Test
    void givenListOfTwoPersonWithEqualsAndComparatorByFirstName_whenSort_thenSortedByFirstNames() {
        Person.PersonWithEquals joe = new Person.PersonWithEquals("Joe", "Portman");
        Person.PersonWithEquals allan = new Person.PersonWithEquals("Allan", "Dale");

        List<Person.PersonWithEquals> people = new ArrayList<>();
        people.add(joe);
        people.add(allan);

        Comparator<Person.PersonWithEquals> compareByFirstNames = new Comparator<Person.PersonWithEquals>() {
            @Override
            public int compare(Person.PersonWithEquals o1, Person.PersonWithEquals o2) {
                return o1.firstName().compareTo(o2.firstName());
            }
        };
        people.sort(compareByFirstNames);

        assertThat(people).containsExactly(allan, joe);
    }

    @Test
    void givenListOfTwoPersonWithEqualsAndComparatorByFirstNameFunctionalStyle_whenSort_thenSortedByFirstNames() {
        Person.PersonWithEquals joe = new Person.PersonWithEquals("Joe", "Portman");
        Person.PersonWithEquals allan = new Person.PersonWithEquals("Allan", "Dale");

        List<Person.PersonWithEquals> people = new ArrayList<>();
        people.add(joe);
        people.add(allan);

        Comparator<Person.PersonWithEquals> compareByFirstNames = Comparator.comparing(Person.PersonWithEquals::firstName);
        people.sort(compareByFirstNames);

        assertThat(people).containsExactly(allan, joe);
    }

    @Test
    void givenTwoPersonWithEqualsAndComparableUsingComparatorAndConsecutiveLastNames_whenCompareTo_thenNegative() {
        Person.PersonWithEqualsAndComparableUsingComparator richard = new Person.PersonWithEqualsAndComparableUsingComparator("Richard", "Jefferson");
        Person.PersonWithEqualsAndComparableUsingComparator joe = new Person.PersonWithEqualsAndComparableUsingComparator("Joe", "Portman");

        assertThat(richard.compareTo(joe)).isNegative();
    }

    @Test
    void givenTwoPersonWithEqualsAndComparableUsingComparatorAndSameLastNames_whenReversedCompareTo_thenZero() {
        Person.PersonWithEqualsAndComparableUsingComparator richard = new Person.PersonWithEqualsAndComparableUsingComparator("Richard", "Jefferson");
        Person.PersonWithEqualsAndComparableUsingComparator mike = new Person.PersonWithEqualsAndComparableUsingComparator("Mike", "Jefferson");

        assertThat(richard.compareTo(mike)).isPositive();
    }

    @Test
    void givenTwoPersonWithEqualsAndComparableUsingComparatorAndConsecutiveLastNames_whenReversedCompareTo_thenPositive() {
        Person.PersonWithEqualsAndComparableUsingComparator richard = new Person.PersonWithEqualsAndComparableUsingComparator("Richard", "Jefferson");
        Person.PersonWithEqualsAndComparableUsingComparator joe = new Person.PersonWithEqualsAndComparableUsingComparator("Joe", "Portman");

        assertThat(joe.compareTo(richard)).isPositive();
    }

    @Test
    void givenTwoPersonWithEqualsAndComparableUsingComparatorAndSameLastNames_whenSortedSet_thenProblem() {
        Person.PersonWithEqualsAndComparableUsingComparator richard = new Person.PersonWithEqualsAndComparableUsingComparator("Richard", "Jefferson");
        Person.PersonWithEqualsAndComparableUsingComparator mike = new Person.PersonWithEqualsAndComparableUsingComparator("Mike", "Jefferson");

        SortedSet<Person.PersonWithEqualsAndComparableUsingComparator> people = new TreeSet<>();
        people.add(richard);
        people.add(mike);

        assertThat(people).containsExactly(mike, richard);
    }
}
